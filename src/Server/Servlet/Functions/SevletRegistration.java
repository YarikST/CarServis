package Server.Servlet.Functions;

/**
 * Created by admin-iorigins on 01.12.16.
 */

import Data.BaseMD5;
import Data.BaseSQL;
import Server.Servlet.AbstractServlerPoolDataBase;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.concurrent.RecursiveAction;


@WebServlet(
        asyncSupported = true
)
public class SevletRegistration extends AbstractServlerPoolDataBase {
    public void init() throws ServletException {
        super.init();
        this.log = Logger.getLogger("SevletRegistration");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.pool.execute(new SevletRegistration.Run(BaseSQL.getBase(this.inetAddress, this.name, this.pass), req.startAsync()));
    }

    private class Run extends RecursiveAction {
        private BaseSQL baseSQL;
        private AsyncContext asyncContext;
        private HttpServletRequest httpServletRequest;
        private HttpServletResponse httpServletResponse;
        private boolean rez;
        private String login;
        private String pass;
        private int id_Mashunu;

        public Run(BaseSQL baseSQL, AsyncContext asyncContext) {
            this.baseSQL = baseSQL;
            this.asyncContext = asyncContext;
            this.httpServletRequest = (HttpServletRequest)asyncContext.getRequest();
            this.httpServletResponse = (HttpServletResponse)asyncContext.getResponse();
        }

        protected void compute() {
            init();
            perevirka();
            if(rez) {
                String arg[] = {"Автентифікація", " id_Машини,Email,Password", id_Mashunu+","+login+","+pass};
                baseSQL.setTable(arg);
            }
            outKlient();
        }

        private void perevirka(){
            String arg[] = {"*","Автентифікація", "Email="+login+" AND "+"Password="+pass};

            CachedRowSet table = baseSQL.getTable(arg);
            try {
                rez = !table.next();
            } catch (SQLException e) {
                if(log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            }
        }

        private void init() {
            login =httpServletRequest.getParameterValues("Email")[0];
            pass = httpServletRequest.getParameterValues("Password")[0];
            id_Mashunu = Integer.valueOf(httpServletRequest.getParameterValues("id_Машини")[0]);

            BaseMD5 baseMD5 = new BaseMD5();
            pass = baseMD5.coder(pass);
            pass = "\"" + pass + "\"";
        }

        private void outKlient() {

            PrintWriter writer = null;
            try {
                writer = this.httpServletResponse.getWriter();
                if (rez) {
                    writer.print("{\"status\":\"OK\"}");
                } else {
                    writer.print("{\"status\":\"ERROR\"}");
                }
            } catch (IOException e) {
                if(log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            } finally {
                writer.flush();
                writer.close();

            }

        }
    }
}

