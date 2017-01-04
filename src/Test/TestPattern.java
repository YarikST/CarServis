package Test;

import java.util.StringTokenizer;

/**
 * Created by admin-iorigins on 16.11.16.
 */
public class TestPattern {
    public static void main(String[] args) {;
        String str = "SELECT ? FROM ? WHERE ? LIMIT";
        StringTokenizer stringTokenizer = new StringTokenizer(str, "?");

        String s = str.replaceFirst("\\?", "0");

        System.out.println(str);

    }
}
