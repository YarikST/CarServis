package Data;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin-iorigins on 01.12.16.
 */
public class BaseMD5 {
    private MessageDigest messageDigest;

    public BaseMD5() {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public String coder(String s) {
        String dec = null;
        messageDigest.reset();
        try {
            messageDigest.update(s.getBytes("utf-8"));
            dec = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dec;
    }


}
