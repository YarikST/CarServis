//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Server.Servlet.Functions;

import Data.BaseMD5;
import Data.BaseSQL;
import Server.Servlet.AbstractServlerPoolDataBase;
import Server.Servlet.Filter.AutuntificationServletFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.RecursiveAction;

@WebServlet(
        asyncSupported = true
)
public class ServletAuthorizable extends AbstractServlerPoolDataBase {
    public ServletAuthorizable() {
    }

    public void init() throws ServletException {
        super.init();
        this.log = Logger.getLogger("ServletAuthorizable");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.pool.execute(new ServletAuthorizable.Run(BaseSQL.getBase(this.inetAddress, this.name, this.pass), req.startAsync()));
    }

    private class Run extends RecursiveAction {
        private BaseSQL baseSQL;
        private AsyncContext asyncContext;
        private HttpServletRequest httpServletRequest;
        private HttpServletResponse httpServletResponse;
        private boolean rez;
        private String login;
        private String pass;
        private String passUser;

        public Run(BaseSQL baseSQL, AsyncContext asyncContext) {
            this.baseSQL = baseSQL;
            this.asyncContext = asyncContext;
            this.httpServletRequest = (HttpServletRequest)asyncContext.getRequest();
            this.httpServletResponse = (HttpServletResponse)asyncContext.getResponse();
        }

        protected void compute() {
            init();
            rez = this.isAuthorizable();
            outKlient();
        }

        private void init() {

            this.login = this.httpServletRequest.getParameterValues("Email")[0];
            this.pass = this.httpServletRequest.getParameterValues("Password")[0];

            log.log(Level.INFO,login+":"+pass);

            if(this.httpServletRequest.getParameter(AutuntificationServletFilter.typeUser) != null) {
                this.passUser = this.httpServletRequest.getParameterValues(AutuntificationServletFilter.typeUser)[0];
            } else {
                this.passUser = AutuntificationServletFilter.user;
            }

           /* HttpSession session = this.httpServletRequest.getSession();
            session.setAttribute(AutuntificationServletFilter.typeUser, this.passUser);*/
        }

        private boolean isAuthorizable() {
            String[] arg = new String[]{"Password", "Автентифікація", "Email=" + this.login};
            BaseMD5 baseMD5 = new BaseMD5();

            try {
                CachedRowSet e = this.baseSQL.getTable(arg);


                    if(!e.next()) {
                        return false;
                    }
                return baseMD5.coder(this.pass).equals(e.getString(1));
            } catch (Exception var4) {
                if(ServletAuthorizable.this.log.isTraceEnabled()) {
                    ServletAuthorizable.this.log.log(Level.TRACE, "Exception", var4);
                }

                return false;
            }
        }

        private void outKlient() {

            PrintWriter writer = null;
            try {

              writer  = this.httpServletResponse.getWriter();
                Cookie e = null;
                if (this.rez) {
                    e = new Cookie("pass", (new BaseMD5()).coder(this.pass));
                    writer.println("{\"status\":\"OK\",\"pass\":\""+(new BaseMD5()).coder(this.pass)+"\"}");
                } else {
                  e=  new Cookie("pass", null);
                    writer.println("{\"status\":\"ERROR\"}");
                }

                this.httpServletResponse.addCookie(e);
            } catch (IOException e) {
                if(ServletAuthorizable.this.log.isTraceEnabled()) {
                    ServletAuthorizable.this.log.log(Level.TRACE, "Exception", e);
                }
            } finally {
                    writer.flush();
                    writer.close();
            }

        }
    }
}
