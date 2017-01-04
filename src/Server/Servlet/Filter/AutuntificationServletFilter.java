package Server.Servlet.Filter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by admin-iorigins on 14.11.16.
 */
@WebServlet(asyncSupported = true)
public class AutuntificationServletFilter implements Filter {
    private String string[];
    public static String typeUser,root,user,pass;

    private Logger log;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log = Logger.getLogger("AutuntificationServletFilter");
        typeUser = "typeUser";
        root = "root";
        user ="user";
        pass = filterConfig.getInitParameter("pass");
        String string = filterConfig.getInitParameter("AutuntificationParameters");
        StringTokenizer stringTokenizer = new StringTokenizer(string,",");
        this.string = new String[stringTokenizer.countTokens()];

        for (int i = 0; i < this.string.length; i++) {
            this.string[i] = stringTokenizer.nextToken();
        }

        Arrays.sort(this.string);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String s = httpServletRequest.getParameterValues("method")[0];

        int i = Arrays.binarySearch(string, s);

        if (i >= 0) {

            if (isPass(httpServletRequest)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                try {
                   httpServletRequest.getServletContext().getRequestDispatcher("/error.html").forward(servletRequest, servletResponse);
                } catch (Exception e) {
                    if (log.isTraceEnabled()) {
                        log.log(Level.TRACE, "Exception", e);
                    }
                }
            }

        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isPass(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        String pass = (String) session.getAttribute(typeUser);
        if (pass == null) {
            return false;
        }
        return pass.equals(root);
    }


    @Override
    public void destroy() {

    }
}
