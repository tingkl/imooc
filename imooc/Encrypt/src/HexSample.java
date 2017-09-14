import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/**
 * Created by tingkl on 2017/9/6.
 */
public class HexSample {
    public void hex(String text) throws UnsupportedEncodingException, DecoderException {
        // 编码
        byte data[] = text.getBytes("UTF-8");
        char[] encodeData = Hex.encodeHex(data);
        String encodeStr = Hex.encodeHexString(data);
        System.out.println(new String(encodeData));
        System.out.println(encodeStr);
        // 解码
        byte[] decodeData = Hex.decodeHex(encodeData);
        System.out.println(new String(decodeData, "UTF-8"));
        // e4b881
    }

    @Test
    public void test() throws UnsupportedEncodingException, DecoderException {
        hex("丁");
        hex("a");
    }
}
