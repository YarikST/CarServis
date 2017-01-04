package Server.Servlet.Context;

import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by admin-iorigins on 20.11.16.
 */
public class LogContextListener implements ServletContextListener {
    private Path path = Paths.get("/var/lib/tomcat7/webapps/project/WEB-INF/logger.xml");

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DOMConfigurator.configure(path.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
