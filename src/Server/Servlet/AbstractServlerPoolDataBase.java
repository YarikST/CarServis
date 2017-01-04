package Server.Servlet;

import javax.servlet.ServletException;

/**
 * Created by admin-iorigins on 14.11.16.
 */
public abstract class AbstractServlerPoolDataBase extends AbstractServletForkJoniPool {
    protected String inetAddress;
    protected String name , pass;

    @Override
    public void init() throws ServletException {
        super.init();
        name = pass = "root";
        inetAddress = getInitParameter("base");
    }
}
