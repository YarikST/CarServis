package Server.Servlet.Filter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by admin-iorigins on 07.11.16.
 */
@WebServlet(asyncSupported = true)
public class CountServletFilter implements Filter {

    private int limit;
    private  int k;
    private FilterConfig filterConfig;
    private Logger log = Logger.getLogger("CountServletFilter");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.log(Level.INFO,"init CountSrvletFileter");
        this.filterConfig = filterConfig;

        String limit = filterConfig.getInitParameter("limit");
        if (limit != null) {
            this.limit = Integer.parseInt(limit);
        } else {
            this.limit = 50;
        }
        log.log(Level.INFO,"limit "+limit);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.log(Level.INFO,"do Filter count");
        try {
            if (k >= limit) {
                log.log(Level.DEBUG, "k>limit");
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Тимчасово недоступно");

            } else {
                log.log(Level.DEBUG, "k<limit");
                k++;
                filterChain.doFilter(servletRequest, servletResponse);
                k--;
            }
        } catch (Throwable e) {
            if (log.isTraceEnabled()) {
                log.log(Level.TRACE, "Exception", e);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
