package Server.Servlet.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.*;


/**
 * Created by admin-iorigins on 07.11.16.
 */

@WebListener
public  class ContextListener implements ServletContextListener {
    private ExecutorService pool;

    public ContextListener() {
    //    pool = Executors.newFixedThreadPool(3, new LogThreadFactory("poll"));
        pool = new ForkJoinPool();
     //   pool = Executors.newCachedThreadPool(new LogThreadFactory("pool"));
     //   Thread.setDefaultUncaughtExceptionHandler(new LogThreadFactory.UncaughtExceptionHandlerLog("poll"));
    }


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("poll", pool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pool.shutdownNow();
    }

    private static class LogThreadFactory implements ThreadFactory{

        private ThreadGroup threadGroup;
        private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

        public LogThreadFactory(String s) {
            threadGroup = new ThreadGroup(s);
            uncaughtExceptionHandler = new UncaughtExceptionHandlerLog("exception");
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(threadGroup, runnable);
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            return thread;
        }

        private static  class UncaughtExceptionHandlerLog implements Thread.UncaughtExceptionHandler{
            private Logger log;

            public UncaughtExceptionHandlerLog(String s) {
                log = Logger.getLogger(s);
            }

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                if (log.isTraceEnabled()) {
                    log.log(Level.TRACE, "uncaught", throwable);
                }
            }
        }
    }
}