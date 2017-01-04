package Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by admin-iorigins on 17.11.16.
 */
public class TestCociesAndParameters extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();

        writer.println("cocies");
        Cookie mas[] = req.getCookies();
        if(mas!=null) {
            for (int i = 0; i < mas.length; i++) {
                writer.println(mas[i].getName());
                writer.println();
                writer.println(mas[i].getValue());
            }
        }

        Enumeration<String> parameterNames = req.getParameterNames();

        writer.println("par");
        while (parameterNames.hasMoreElements()) {

                writer.println(req.getParameterValues(parameterNames.nextElement()));

        }

        writer.flush();
        writer.close();
    }
}
