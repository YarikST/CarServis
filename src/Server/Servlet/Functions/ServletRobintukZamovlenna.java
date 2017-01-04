package Server.Servlet.Functions;

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
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin-iorigins on 04.12.16.
 */
@WebServlet(asyncSupported = true)
public class ServletRobintukZamovlenna extends AbstractServlerPoolDataBase {

    private HashMap<Integer,Lock> map;

    @Override
    public void init() throws ServletException {
        super.init();
        log = Logger.getLogger("ServletRobintukZamovlenna");

        map = new HashMap<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Run run = new Run(BaseSQL.getBase(inetAddress, name, pass), req.startAsync(), null);
        log.log(Level.INFO,"do get");
        synchronized (map){
            Lock lock = map.get(run.getId_F());
            if (lock == null) {
                lock = new ReentrantLock();
                map.put(run.getId_F(), lock);
            }
            run.lock = lock;
        }
        pool.execute(run);

    }

    private class Run implements Runnable{
        private BaseSQL baseSQL;
        private AsyncContext asyncContext;
        private HttpServletResponse httpServletResponse;
        private HttpServletRequest httpServletRequest;

        private Lock lock;

        private int id_R,id_F,id_Z;

        private boolean rez;

        public Run(BaseSQL baseSQL, AsyncContext asyncContext,Lock lock) {
            this.baseSQL = baseSQL;
            this.asyncContext = asyncContext;
            this.lock = lock;

            httpServletRequest = (HttpServletRequest) asyncContext.getRequest();
            httpServletResponse = (HttpServletResponse) asyncContext.getResponse();

            init();
        }

        public int getId_R() {
            return id_R;
        }

        public int getId_F() {
            return id_F;
        }

        public int getId_Z() {
            return id_Z;
        }

        private void  init(){
            id_F = Integer.valueOf(httpServletRequest.getParameterValues("id_Furmu")[0]);
            id_R = Integer.valueOf(httpServletRequest.getParameterValues("id_Robitnuka")[0]);
            id_Z = Integer.valueOf(httpServletRequest.getParameterValues("id_Zamovlenna")[0]);
        }

        @Override
        public void run() {

            boolean b;
            synchronized (map) {
                b = lock.tryLock();
               if(b){ lock.lock();}
            }
            log.log(Level.INFO,"b "+b);
            if (b) {
                base();
                lock.unlock();
                outKlient();
            } else {
              if(!isZmina()){;
                  base();
                  lock.unlock();
                  outKlient();
              }else{
                  lock.unlock();
                  rez = false;
                  outKlient();
              }
            }
        }

        private boolean isZmina(){
            String arg[] = {"Черга", "id_Фірми=" + id_F + " AND " + "id_Замовлення=" + id_Z};

            CachedRowSet table = baseSQL.getTable(arg);


            try {
                return !table.next();
            } catch (SQLException e) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "Exception", e);
                }
            }
            return false;
        }
        private void base() {
            String arg[] = {"Черга", "id_Фірми=" + id_F + " AND " + "id_Замовлення=" + id_Z};
            baseSQL.removeTable(arg);

            String arg2[] = {"Список_Замовлень","id_Робітника,id_Замовлення,Статус",id_R+","+id_Z+","+"\"Ремонт\""};
            baseSQL.setTable(arg2);

            rez = true;

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
