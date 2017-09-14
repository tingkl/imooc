import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tingkl on 2017/9/7.
 */
public class SHA {
    private static String src = "tingkl信息摘要SHA";
    @Test
    public void jdkSHA1() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(src.getBytes());
        System.out.println("jdk sha-1:" + Hex.encodeHexString(md.digest()));
    }

    @Test
    public void bcSHA1() {
        Digest digest = new SHA1Digest();
        digest.update(src.getBytes(), 0,  src.getBytes().length);
        byte[] sha1Bytes  = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes, 0);
        System.out.println("bc sha-1:" + Hex.encodeHexString(sha1Bytes));
    }

    @Test
    public void compare() throws NoSuchAlgorithmException {
        jdkSHA1();
        bcSHA1();
    }
}
