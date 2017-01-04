package Server.Servlet;

import Data.BaseSQL;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebServlet(asyncSupported = true)
public class ServletSQL extends AbstractServlerPoolDataBase {

    @Override
    public void init() throws ServletException {
        super.init();
        log = Logger.getLogger("ServletSQL");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Run run = new Run(BaseSQL.getBase(inetAddress, name, pass), req.startAsync());

            HttpSession session = req.getSession();

            List<Run> runSession = (List<Run>) session.getAttribute("run");

            if (runSession != null) {
                int i = runSession.indexOf(run);
                if (i != -1) {
                    run = runSession.get(i);
                    run.initializable(req.getAsyncContext());
                } else {
                    run.init();
                    runSession.add(run);

                }
            } else {
                run.init();

                ArrayList<Run> runs = new ArrayList<>();
                runs.add(run);
                session.setAttribute("run", runs);
            }


            pool.execute(run);
        } catch (Throwable e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }


    private class Run implements Runnable {

        private BaseSQL baseSQL;
        private AsyncContext asyncContext;
        private HttpServletResponse httpServletResponse;
        private HttpServletRequest httpServletRequest;

        private Cookie cookies[];

        private Method method;
        private String arg[];

        private CachedRowSet cachedRowSet;


        public Run(BaseSQL baseSQL, AsyncContext asyncContext) {
            this.baseSQL = baseSQL;
            initializable(asyncContext);
            try {
                arg = httpServletRequest.getParameterValues("arg");
                method = baseSQL.getClass().getMethod(httpServletRequest.getParameterValues("method")[0],arg.getClass());
            } catch (NoSuchMethodException e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            }
        }

        public void initializable(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
            httpServletRequest = (HttpServletRequest) asyncContext.getRequest();
            httpServletResponse = (HttpServletResponse) asyncContext.getResponse();

            httpServletResponse.setContentType("application/json; charset=UTF-8");
        }

        @Override
        public void run() {
            try {
                if (cachedRowSet != null) {
                    write();
                    if (!cache()) {
                        clearSession();
                    }
                }
            } catch (Throwable e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            }
        }

        private void init() {
            cookies = httpServletRequest.getCookies();
            cachedRowSet = base();
        }

        private Cookie getCookies(String name) {
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        return cookie;
                    }
                }
            }
            return null;
        }

        private boolean cache() {
            try {
                return cachedRowSet.nextPage();
            } catch (Throwable e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception(no next Pages)", e);
                }
            }
            return false;
        }

        private void clearSession() {
            List<Run> runs = (List<Run>) httpServletRequest.getSession().getAttribute("run");

            runs.remove(this);
        }

        private CachedRowSet base() {
            try {
              return   cachedRowSet = (CachedRowSet) method.invoke(baseSQL, (Object) arg);
            } catch (Throwable e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            }
            return null;
        }

        private void write() {
            JsonGenerator generator = null;
            try {
                generator = Json.createGenerator(httpServletResponse.getWriter());
                generator.writeStartArray();
                while (cachedRowSet.next()) {
                    ResultSetMetaData metaData = cachedRowSet.getMetaData();

                    generator.writeStartObject();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String str = cachedRowSet.getString(i);

                        if (str == null) {
                            str = "";
                        }

                        generator.write(metaData.getColumnName(i), str);//
                    }
                    generator.writeEnd();
                }
                generator.writeEnd();

            } catch (Throwable e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            } finally {
                generator.flush();
                generator.close();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Run run = (Run) o;

            if (!method.equals(run.method)) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(arg, run.arg);

        }

        @Override
        public int hashCode() {
            int result = method.hashCode();
            result = 31 * result + Arrays.hashCode(arg);
            return result;
        }

    }
}
