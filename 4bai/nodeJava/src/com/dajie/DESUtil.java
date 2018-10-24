package com.dajie;

import junit.framework.Assert;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class DESUtil {
    private static final byte[] IV = new byte[]{0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7};
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String encrypt(String plainText, String password) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(toKey(password), "DES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(DEFAULT_CHARSET));

            return new String(Base64.getUrlEncoder().encode(cipherBytes), DEFAULT_CHARSET);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String cipherText, String password) {
        try {
            byte[] cipherBytes = Base64.getUrlDecoder().decode(cipherText.getBytes(DEFAULT_CHARSET));

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(toKey(password), "DES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            return new String(cipher.doFinal(cipherBytes), DEFAULT_CHARSET);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] toKey(String s) {
        try {
            MessageDigest digestAlgorithm = MessageDigest.getInstance("MD5");
            digestAlgorithm.reset();
            digestAlgorithm.update(s.getBytes());
            byte[] md5Bytes = digestAlgorithm.digest();

            byte[] resultKey = new byte[8];
            System.arraycopy(md5Bytes, 0, resultKey, 0, resultKey.length);

            return resultKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String password = "test";
        String plainText = "\"basic\":{\"name\":\"王一\",\"gender\":\"男\",\"birthday\": \"1998-10-01\",\"idCardNum\": \"100110198006010123\",\"workYears\": 3,\"language\": \"英语,法语,德语\"}";
        String secretData = encrypt(plainText, password);
        String decryptData = decrypt(secretData, password);
        // Assert.assertEquals(decryptData, plainText);
        System.out.println(secretData);

        String secretDataWithJdk6 = encryptWithJdk6(plainText, password);
        String decryptDataWithJdk6 = decryptWithJdk6(secretDataWithJdk6, password);
        Assert.assertEquals(decryptDataWithJdk6, plainText);
    }


    public static String encryptWithJdk6(String plainText, String password) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(toKey(password), "DES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(DEFAULT_CHARSET));

            return new BASE64Encoder().encode(cipherBytes).replaceAll("\\+", "-").replaceAll("/", "_");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptWithJdk6(String cipherText, String password) {
        try {
            byte[] cipherBytes = new BASE64Decoder().decodeBuffer(cipherText.replaceAll("-", "\\+").replaceAll("_", "/"));

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(toKey(password), "DES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            return new String(cipher.doFinal(cipherBytes), DEFAULT_CHARSET);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
