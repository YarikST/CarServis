//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Server.Servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public abstract class AbstractServletForkJoniPool extends HttpServlet implements AutoCloseable {
    protected ForkJoinPool pool;
    protected Logger log;

    public AbstractServletForkJoniPool() {
    }

    public void init() throws ServletException {
        super.init();
        this.pool = (ForkJoinPool)this.getServletContext().getAttribute("poll");
    }

    public void close() throws Exception {
        super.clone();
        this.pool.shutdown();
        this.pool.awaitTermination(10L, TimeUnit.SECONDS);
        this.pool.shutdownNow();
    }
}
