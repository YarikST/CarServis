package Test;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by admin-iorigins on 20.11.16.
 */
public class LoggerInit {
    public static void main(String[] args) {
        String path = "/home/admin-iorigins/IdeaProjects/FirstGlobalProject/src/Test/prom.xml";
        DOMConfigurator.configure(path);

        Logger logger = Logger.getLogger("testLoger");

        logger.info("info",new NullPointerException("null"));
        logger.warn("wran46");


    }
}
