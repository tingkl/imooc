import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tingkl on 2017/9/7.
 */
public class AES {
    // 对称加密方式
    private static String src = "中文tingkl";
    static String hexKey;
    static String base64Key;
    static {
        // 生成KEY
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            // 密钥是没有意义的56个字节，如果使用utf-8编码方式显示成字符串（new String(result)）会是乱码
            // 所以一般直接显示密文的16进制字符串
            hexKey = Hex.encodeHexString(bytesKey);
            System.out.println("hexKey:" + hexKey);
            base64Key = Base64.encodeBase64String(bytesKey);
            System.out.println("base64Key:" + base64Key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static Key getSecretKey(byte[] bytesKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        // KEY转换
        Key key = new SecretKeySpec(bytesKey, "AES");
        return key;
    }

    public byte[] encrypt(String src, Key secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(src.getBytes());
        return result;
    }

    private String decrypt(byte[] encryptBytesReceived, Key secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(encryptBytesReceived);
        return new String(result);
    }

    @Test
    public void jdkAESHex() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, DecoderException, InvalidKeySpecException {
        Key secretKeyHex = getSecretKey(Hex.decodeHex(hexKey.toCharArray()));
        // 加密
        byte[] encryptBytesToSend = encrypt(src, secretKeyHex);
        // 加密以后，不能用new String(result)来显示，因为加密完后是没有意义的一堆字节，
        // 如果使用魔种编码方式显示成字符串，会是乱码，所以一般直接显示密文的16进制字符串
        // 编码
        String hexEncryptBytes = Hex.encodeHexString(encryptBytesToSend);
        System.out.println("aes encrypt:" + hexEncryptBytes);

        // 网络传输时用hex，比如http传参

        // 解码
        byte[] encryptBytesReceived = Hex.decodeHex(hexEncryptBytes.toCharArray());
        // 解密
        String result = decrypt(encryptBytesReceived, secretKeyHex);
        System.out.println("aes decrypt:" + result);
    }

    @Test
    public void jdkAESBase64() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        Key secretKeyBase64 = getSecretKey(Base64.decodeBase64(base64Key));
        byte[] encryptBytesToSend = encrypt(src, secretKeyBase64);
        // 加密以后，不能用new String(result)来显示，因为加密完后是没有意义的一堆字节，
        // 如果使用魔种编码方式显示成字符串，会是乱码，所以一般直接显示密文的16进制字符串
        // 编码
        String hexEncryptBytes = Base64.encodeBase64String(encryptBytesToSend);
        System.out.println("aes encrypt:" + hexEncryptBytes);

        // 网络传输时，一般用Base64（因为base64编码是数据的4/3,而hex编码数据增长一倍），比如http传参

        // 解码
        byte[] encryptBytesReceived = Base64.decodeBase64(hexEncryptBytes);
        // 解密
        // 接受到网络传过来的参数，先转成密文字节数组
        String result = decrypt(encryptBytesReceived, secretKeyBase64);
        System.out.println("aes decrypt:" + result);
    }


    @Test
    public void compare() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, DecoderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        jdkAESHex();
        jdkAESBase64();
        /*
        网络传输时，一般用Base64（因为base64编码是数据的4/3,而hex编码数据增长一倍），比如http传参
        hexKey:52a3968b25ba3695657d2930f0b2ee48
        base64Key:UqOWiyW6NpVlfSkw8LLuSA==
        aes encrypt:c26dc0a7eb32b956c8f8631f66dca5c079c22ad11f157cd40ba0abba5b37c860
        aes decrypt:tingkl security aes
        aes encrypt:wm3Ap+syuVbI+GMfZtylwHnCKtEfFXzUC6Cruls3yGA=
        aes decrypt:tingkl security aes
        * */
    }
}
