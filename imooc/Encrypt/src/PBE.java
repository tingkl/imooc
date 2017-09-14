import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tingkl on 2017/9/7.
 */
public class PBE {

    private static String src = "tingkl security pbe";

    @Test
    public void jdkPBE() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);

        // 口令与密钥
        String password = "tingkl";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        Key key = factory.generateSecret(pbeKeySpec);

        // 加密
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");

        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] result = cipher.doFinal(src.getBytes());

        System.out.println("jdk pbe encrypt:" + Base64.encodeBase64String(result));


        // 解密
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        result = cipher.doFinal(result);
        System.out.println("jdk pbe decrypt:" + new String(result));


    }

}
