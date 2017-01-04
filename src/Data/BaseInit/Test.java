package Data.BaseInit;

import Data.BaseInit.BaseRead.FileGenerator;
import Data.BaseSQL;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by admin-iorigins on 24.11.16.
 */
public class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        Properties properties = System.getProperties();
        String nameDriver = "jdbc.drivers";
        String classDriver = "com.mysql.jdbc.Driver";
        properties.setProperty(nameDriver, classDriver);

        try {
            Class.forName(classDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        BaseSQL baseSQL = BaseSQL.getBase("jdbc:mysql://localhost/ProjectCar_CTO", "root", "root");
        Method method = baseSQL.getClass().getMethod("setTable", String[].class);

      //  new Task(baseSQL,method,new FileGenerator(Paths.get("/home/admin-iorigins/Завантаження/руля/mashina_dani.txt"))).run();
      //  new Task(baseSQL,method,new FileGenerator(TestContext.черга)).run();

        /*Path path1 = Paths.get("/home/admin-iorigins/Робочий стіл/project/base");
        run(path1, baseSQL, method);*/
       // Path path2 = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані");
        Path path2 = Paths.get("/home/admin-iorigins/Завантаження/руля");
        run(path2, baseSQL, method);


    }

    private static  void run(Path path,BaseSQL baseSQL,Method method) {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            for (Path path1 : directoryStream) {
                if (Files.isRegularFile(path1)) {
                    new Task(baseSQL,method,new FileGenerator(path1)).run();
                    System.out.println(path1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
