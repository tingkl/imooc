import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tingkl on 2017/9/7.
 */
public class MD {
    private static String src = "tingkl信息摘要MD";
    @Test
    public void jdkMD5() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(src.getBytes());
        System.out.println("JDK MD5:" + Hex.encodeHexString(md5Bytes));
    }

    @Test
    public void jdkMD2() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD2");
        byte[] md5Bytes = md.digest(src.getBytes());
        System.out.println("JDK MD2:" + Hex.encodeHexString(md5Bytes));
    }

    @Test
    public void compare() throws NoSuchAlgorithmException {
        jdkMD5();
        jdkMD2();
    }
}
