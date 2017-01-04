package Test;

import Data.BaseMD5;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin-iorigins on 01.12.16.
 */
public class TestMD5 {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BaseMD5 baseMD5 = new BaseMD5();

        System.out.println(baseMD5.coder("p1"));


    }
}
