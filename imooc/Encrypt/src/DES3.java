import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tingkl on 2017/9/5.
 */
public class DES3 {

    static String  src = "imooc security 3des";

    static String hexKey;
    static String base64Key;
    static SecretKey secretKeyBase64;
    static SecretKey secretKeyHex;

    // 3des（desede）相比于des，修改点：
    // 1. KeyGenerator.getInstance("DESede")
    // 2. new DESedeKeySpec(bytesKey)
    // 3. SecretKeyFactory.getInstance("DESede");
    // 4. Cipher.getInstance("DESede/ECB/PKCS5Padding");
    // 5. keyGenerator.init(112);
    static {
        // 生成KEY
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            // keyGenerator.init(112);
            // 根据算法，生成key的默认长度 new SecureRandom()
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            // 密钥是没有意义的56个字节，如果使用utf-8编码方式显示成字符串（new String(result)）会是乱码
            // 所以一般直接显示密文的16进制字符串
            hexKey = Hex.encodeHexString(bytesKey);
            System.out.println("hexKey:" + hexKey);
            base64Key = Base64.encodeBase64String(bytesKey);
            System.out.println("base64Key:" + base64Key);
            secretKeyBase64 = getSecretKey(Base64.decodeBase64(base64Key));
            secretKeyHex = getSecretKey(Hex.decodeHex(hexKey.toCharArray()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }

    }

    public static SecretKey getSecretKey(byte[] bytesKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        // KEY转换
        DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = factory.generateSecret(desKeySpec);
        return secretKey;
    }
    public byte[] encrypt(String src,  SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(src.getBytes());
        return result;
    }

    private String decrypt(byte[] encryptBytesReceived, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(encryptBytesReceived);
        return new String(result);
    }
    @Test
    public void jdk3DESHex() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, DecoderException {

        byte[] encryptBytesToSend = encrypt(src, secretKeyHex);
        // 加密以后，不能用new String(result)来显示，因为加密完后是没有意义的一堆字节，
        // 如果使用魔种编码方式显示成字符串，会是乱码，所以一般直接显示密文的16进制字符串
        String hexEncryptBytes = Hex.encodeHexString(encryptBytesToSend);
        System.out.println("des encrypt:" + hexEncryptBytes);

        // 网络传输时用hex，比如http传参
        // 解密
        // 接受到网络传过来的参数，先转成密文字节数组
        byte[] encryptBytesReceived = Hex.decodeHex(hexEncryptBytes.toCharArray());
        String result = decrypt(encryptBytesReceived, secretKeyHex);

        System.out.println("des decrypt:" + result);

    }

    @Test
    public void jdk3DESBase64() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] encryptBytesToSend = encrypt(src, secretKeyBase64);
        // 加密以后，不能用new String(result)来显示，因为加密完后是没有意义的一堆字节，
        // 如果使用魔种编码方式显示成字符串，会是乱码，所以一般直接显示密文的16进制字符串
        String hexEncryptBytes = Base64.encodeBase64String(encryptBytesToSend);
        System.out.println("des encrypt:" + hexEncryptBytes);

        // 网络传输时，一般用Base64（因为base64编码是数据的4/3,而hex编码数据增长一倍），比如http传参
        // 解密
        // 接受到网络传过来的参数，先转成密文字节数组
        byte[] encryptBytesReceived = Base64.decodeBase64(hexEncryptBytes);
        String result = decrypt(encryptBytesReceived, secretKeyBase64);

        System.out.println("des decrypt:" + result);
    }


    @Test
    public void compare() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, DecoderException, NoSuchPaddingException, InvalidKeyException {
        jdk3DESHex();
        jdk3DESBase64();

        /*
        网络传输时，一般用Base64（因为base64编码是数据的4/3,而hex编码数据增长一倍），比如http传参
        hexKey:   32ced68c3e62863b
        base64Key:Ms7WjD5ihjs=
        des encrypt:d1d34882569596369bd991d9fa7a2fbd
        des decrypt:imooc security
        des encrypt:0dNIglaVljab2ZHZ+novvQ==
        des decrypt:imooc security
        * */
    }

}
