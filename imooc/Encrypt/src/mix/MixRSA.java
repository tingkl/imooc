package mix;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by tingkl on 2017/9/5.
 */
public class MixRSA {
    // keySize512字节时，要加密的数据不能大于53字节，分块加解密（每块必须为2的倍数，加密32字节一块，解密64字节一块）
    public static void main(String[] args) {
        Msg msg = new Msg();
        try {
            if (args.length < 2) {
                if (args.length == 1 && "keyPair".equals(args[0])) {
                    Properties properties = generateKeyPair();
                    msg.setPrivateKey(properties.getProperty("private"));
                    msg.setPublicKey(properties.getProperty("public"));
                    msg.setSuccess(true);
                    msg.setMsg("keyPair generated: ./keys");
                    System.out.println(msg);
                } else {
                    msg.setMsg("method text, need two params!");
                    System.out.println(msg);
                }
            } else {
                String filePath = null;
                if (args.length > 2) {
                    filePath = args[2];
                }
                Properties properties = loadKey(filePath);
                if (properties != null) {
                    String method = args[0];
                    String text = args[1];
                    msg.setMethod(method);
                    msg.setText(text);
                    switch (method) {
                        case "encrypt":
                            String publicKeyBase64 = properties.getProperty("public");
                            if (publicKeyBase64 != null) {
                                String encryptBytesBase64 = encrypt(publicKeyBase64, text);
                                msg.setMsg(encryptBytesBase64);
                                msg.setSuccess(true);
                                msg.setPublicKey(publicKeyBase64);
                                System.out.println(msg);
                            } else {
                                msg.setMsg("publicKey not found!");
                            }
                            break;
                        case "decrypt":
                            String privateKeyBase64 = properties.getProperty("private");
                            if (privateKeyBase64 != null) {
                                String clearText = decrypt(privateKeyBase64, text);
                                msg.setMsg(clearText);
                                msg.setSuccess(true);
                                msg.setPrivateKey(privateKeyBase64);
                                System.out.println(msg);
                            } else {
                                msg.setMsg("privateKey not found!");
                            }
                            break;
                        default:
                            msg.setMsg("only encrypt, decrypt, keyPair are permitted!");
                            System.out.println(msg);
                            return;
                    }
                } else {
                    msg.setMsg(filePath + " not exist!");
                }
            }
        } catch (Exception e) {
            msg.setMsg(e.getMessage());
            System.out.println(msg);
        }
    }

    public static String encrypt(String text) throws Exception {
        Properties properties = loadKey(null);
        System.out.println(new File("./public.key").getAbsolutePath());
        return encrypt(properties.getProperty("public"), text);
    }

    public static String decrypt(String text) throws Exception {
        Properties properties = loadKey(null);
        return decrypt(properties.getProperty("private"), text);
    }

    public static String encrypt(String publicKeyBase64, String text) throws Exception {
        if (publicKeyBase64 != null) {
            //1.公钥加密，私钥解密----加密
            PublicKey publicKey = getPublicKey(Base64.decodeBase64(publicKeyBase64));
            byte[] encryptBytesToSend = encrypt(text, publicKey);
            // String encryptBytesHex = Hex.encodeHexString(encryptBytesToSend);
            String encryptBytesBase64 = Base64.encodeBase64String(encryptBytesToSend);
            return encryptBytesBase64;
        }
        throw new Exception("publicKey not found!");
    }

    public static String decrypt(String privateKeyBase64, String text) throws Exception {
        if (privateKeyBase64 != null) {
            byte[] encryptBytesReceived = Base64.decodeBase64(text);
            PrivateKey privateKey = getPrivateKey(Base64.decodeBase64(privateKeyBase64));
            String clearText = decrypt(encryptBytesReceived, privateKey);
            return clearText;
        }
        throw new Exception("privateKey not found!");
    }

    public static Properties generateKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyBase64 = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyBase64 = Base64.encodeBase64String(rsaPrivateKey.getEncoded());

        Properties pps = new Properties();

        pps.setProperty("public", publicKeyBase64);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File("./keys");
        if (!file.exists()) {
            file.mkdir();
        }
        pps.store(new FileOutputStream("./keys/public.key"), sdf.format(new Date()));
        pps.setProperty("private", privateKeyBase64);
        pps.store(new FileOutputStream("./keys/private.key"), sdf.format(new Date()));
        return pps;
    }

    public static Properties loadKey(String filePath) throws IOException {
        Properties pps = null;
        File file;
        if (filePath != null) {
            file = new File(filePath);
        } else {
            file = new File("./private.key");
            if (!file.exists()) {
                file = new File("./public.key");
            }
        }
        if (file.exists()) {
            pps = new Properties();
            pps.load(new FileInputStream(file));
        }
        return pps;
    }

    private static byte[] subArray(byte[] bytes, int start, int size) {
        int length = bytes.length;
        byte[] piece;
        if (start + size < length) {
           piece = new byte[size];
        } else {
            piece = new byte[length - start];
        }
        for(int i = 0; i < piece.length; i++) {
            piece[i] = bytes[start + i];
        }
        return piece;
    }

    public static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    private static byte[] encrypt(String src, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密时超过57字节就报错。为此采用分段加密的办法来加密
        List<byte[]> byteList = new ArrayList<byte[]>();
        byte[] bytes = src.getBytes();
        for (int i = 0; i < bytes.length; i += 32) {
            // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byteList.add(cipher.doFinal(subArray(bytes, i, 32)));
        }
        byte[] result = sysCopy(byteList);
        return result;
    }

    private static String decrypt(byte[] encryptBytesReceived, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);

        List<byte[]> byteList = new ArrayList<byte[]>();
        for (int i = 0; i < encryptBytesReceived.length; i += 64) {
            // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byteList.add(cipher.doFinal(subArray(encryptBytesReceived, i, 64)));
        }
        byte[] result = sysCopy(byteList);
        return new String(result);
    }


    private static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA * /None/NoPadding】
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }

    private static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }
//    @Test
//    public void test() throws Exception {
//        String a = encrypt("中文所说的方式是嘎达gas大嘎达嘎达嘎达噶舍得给中文所说的方式是嘎达gas大嘎达嘎达嘎达噶舍得给中文所说的方式是嘎达gas大嘎达嘎达嘎达噶舍得给中文所说的方式是嘎达gas大嘎达嘎达嘎达噶舍得给中文所说的方式是嘎达gas大嘎达嘎达嘎达噶舍得给");
//        System.out.println(a);
//        String b = decrypt(a);
//        System.out.println(b);
//    }
}
