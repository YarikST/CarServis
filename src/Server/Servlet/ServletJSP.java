//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Server.Servlet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.RecursiveAction;

@WebServlet(asyncSupported = true)
public class ServletJSP extends AbstractServletForkJoniPool {
    private char c;
    private Path path;

    public ServletJSP() {
    }

    public void init() throws ServletException {
        super.init();
        this.c = File.separatorChar;
        this.path = Paths.get(this.getInitParameter("path"), new String[0]);
        this.log = Logger.getLogger("ServletJSP");
    }

    protected void doGet( HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        final HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
        final HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

        String s = request.getPathInfo().replaceFirst("/JSP", "");
        this.log.log(Level.INFO, s);
        boolean boo = !s.endsWith(".jsp");
         Path pathFile = Paths.get(this.path.toString() + this.c + s, new String[0]);
        if(!Files.exists(pathFile, new LinkOption[0])) {
            pathFile = Paths.get(this.path.toString() + this.c + "index.html", new String[0]);
            boo = true;
        }

        if(boo) {
            this.pool.execute(new ServletJSP.Run(pathFile, asyncContext));
        } else {
            final Path finalPathFile = pathFile;
            this.pool.execute(new Runnable() {
                public void run() {
                    try {
                        ServletJSP.this.getServletContext().getRequestDispatcher(finalPathFile.toString()).forward(request, response);
                    } catch (Exception var2) {
                        if(ServletJSP.this.log.isTraceEnabled()) {
                            ServletJSP.this.log.log(Level.TRACE, "Exception", var2);
                        }
                    }

                }
            });
        }

    }

    private class Run extends RecursiveAction {
        private Path path;
        private PrintWriter printWriter;
        private Charset charset;

        public Run(Path path, AsyncContext asyncContext) {
            this.path = path;
            ServletResponse response = asyncContext.getResponse();

            response.setContentType("text/html; charset=UTF-8");
            try {
                this.printWriter = response.getWriter();
            } catch (IOException e) {
                if(ServletJSP.this.log.isTraceEnabled()) {
                    ServletJSP.this.log.log(Level.TRACE, "Exception", e);
                }
            }
            this.charset = Charset.forName("UTF-8");
        }

        public Run(Path path, PrintWriter printWriter) {
            this.path = path;
            this.printWriter = printWriter;
        }

        @Override
        protected void compute() {
            FileInputStream stream = null;

            try {
                try {
                    stream = new FileInputStream(this.path.toFile());
                } catch (IOException var13) {
                    if(ServletJSP.this.log.isTraceEnabled()) {
                        ServletJSP.this.log.log(Level.TRACE, "Exception", var13);
                    }
                }

                InputStreamReader e = new InputStreamReader(stream, "UTF-8");

                for(char[] buff = new char[1024]; e.read(buff) != -1; buff = new char[1024]) {
                    this.printWriter.write(buff);
                }
            } catch (IOException var14) {
                if(ServletJSP.this.log.isTraceEnabled()) {
                    ServletJSP.this.log.log(Level.TRACE, "Exception", var14);
                }
            } finally {
                try {
                    stream.close();
                    printWriter.flush();
                    printWriter.close();
                } catch (IOException var12) {
                    if(ServletJSP.this.log.isTraceEnabled()) {
                        ServletJSP.this.log.log(Level.TRACE, "Exception", var12);
                    }
                }

            }

        }
    }
}
