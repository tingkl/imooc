import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by tingkl on 2017/9/6.
 */
public class Base64Sample {
    private static String src = "imooc security base64";

    @Test
    public void  jdkBase64() throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(src.getBytes());
        System.out.println("encode:" + encode);

        BASE64Decoder decoder = new BASE64Decoder();
        System.out.println("decode:" + new String(decoder.decodeBuffer(encode)));
    }

    @Test
    public void commonsCodesBase64 () {
        byte[] encodeBytes = Base64 .encodeBase64(src.getBytes());
        System.out.println("encode:" + new String(encodeBytes));

        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        System.out.println("decode:" + new String(decodeBytes));
    }

    @Test
    public void bouncyCastleBase64() {
        byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
        System.out.println("encode:" + new String(encodeBytes));

        byte[] decodeBytes  = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
        System.out.println("decode:" + new String(decodeBytes));
    }
}
