package Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by admin-iorigins on 04.11.16.
 */

//EHO SERVER
public class TestServletStream extends HttpServlet {

    static int i = 0;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        ForkJoinPool pool = (ForkJoinPool) req.getServletContext().getAttribute("forkJoniFramevork");

        resp.setContentType("text/html");
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en-US\">\n" +
                "<head>\n" +
                "<script type='text/javascript' src='/home/admin-iorigins/Робочий стіл/site/query.js'></script>\n" +
                "</head>\n" +
                "<body>\n");

        writer.println(pool);

        writer.println("attribyte");

        Enumeration<String> attributeNames1 = req.getAttributeNames();
        while (attributeNames1.hasMoreElements()) {
            writer.println(req.getAttribute(attributeNames1.nextElement()));
        }


        req.setAttribute("one" + i++, "one");

        writer.println("attribyte2");

        Enumeration<String> attributeNames2 = req.getAttributeNames();
        while (attributeNames2.hasMoreElements()) {
            writer.println(req.getAttribute(attributeNames2.nextElement()));
        }

        writer.write("</body>\n" +
                "</html>");


        writer.flush();
        writer.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
