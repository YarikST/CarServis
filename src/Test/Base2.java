package Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by admin-iorigins on 10.11.16.
 */
public class Base2 {
    public static void main(String[] args) {
        Path path1 = Paths.get("0/1/2");

        Date data = new Date();
        data.setTime((1<<32));
        System.out.println(data);
        data.setTime((0));
        System.out.println(data);

        data.setTime((10248498));
        System.out.println(data);
    }
}
