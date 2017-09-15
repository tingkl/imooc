import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tingkl on 2017/9/5.
 */
public class RSA {
    private static String src = "====\\tingkl security rs是的嘎的高度嘎达gas大嘎达搜嘎舍得给过分过分过分十大歌手地方a";
    private static String publicKeyBase64;
    private static String privateKeyBase64;

    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            publicKeyBase64 = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            privateKeyBase64 = Base64.encodeBase64String(rsaPrivateKey.getEncoded());

            System.out.println("Public Key:" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("Private Key:" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
    private byte[] sign(String src, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(src.getBytes());
        return signature.sign();
    }
    private boolean verify(byte[] encryptBytesReceived, PublicKey publicKey, String src) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(src.getBytes());
        boolean bool = signature.verify(encryptBytesReceived);
        return bool;
    }

    @Test
    public void signature() throws SignatureException {
        /*
        数字签名算法包含签名和验证两项操作，用私钥进行签名，用公钥进行验证。
        实际运用时，通常先使用消息摘要算法对原始消息做摘要处理，
        然后使用私钥对摘要值做签名处理，在验证签名时，则使用公钥验证码消息的摘要值。
        */
        try {
            //1.签名(MD5 + 私钥加密）
            PrivateKey privateKey = getPrivateKey(Base64.decodeBase64(privateKeyBase64));
            byte[] encryptBytesToSend = sign(src, privateKey);
            String encryptBytesBase64 = Base64.encodeBase64String(encryptBytesToSend);
            System.out.println("签名 + base64编码:" + encryptBytesBase64);

            // 网络传输

            //2.验证(公钥解密 + 对比MD5)
            PublicKey publicKey = getPublicKey(Base64.decodeBase64(publicKeyBase64));
            byte[] encryptBytesReceived = Base64.decodeBase64(encryptBytesBase64);
            boolean bool = verify(encryptBytesReceived, publicKey, src);
            System.out.println("base64解码 + 验证:" + bool);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdkRSA() {
        /*
        *
        * 既然是加密，那肯定是不希望别人知道我的消息，所以只有我才能解密，所以可得出公钥负责加密，私钥负责解密；
        * 同理，既然是签名，那肯定是不希望有人冒充我发消息，只有我才能发布这个签名，所以可得出私钥负责签名，公钥负责验证。
        * */
        try {
            //1.私钥加密，公钥解密----加密
            PrivateKey privateKey = getPrivateKey(Base64.decodeBase64(privateKeyBase64));
            byte[] encryptBytesToSend = encrypt(src, privateKey);
            String encryptBytesBase64 = Base64.encodeBase64String(encryptBytesToSend);

            System.out.println("私钥加密---send:" + encryptBytesBase64);
            //2.私钥加密，公钥解密----解密

            byte[] encryptBytesReceived = Base64.decodeBase64(encryptBytesBase64);
            PublicKey publicKey = getPublicKey(Base64.decodeBase64(publicKeyBase64));
            String result = decrypt(encryptBytesReceived, publicKey);
            System.out.println("公钥解密---receive:" + result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdkRSA2() {
        /*
        * 既然是加密，那肯定是不希望别人知道我的消息，所以只有我才能解密，所以可得出公钥负责加密，私钥负责解密；
        * 同理，既然是签名，那肯定是不希望有人冒充我发消息，只有我才能发布这个签名，所以可得出私钥负责签名，公钥负责验证。
        * */
        try {
            //1.公钥加密，私钥解密----加密
            PublicKey publicKey = getPublicKey(Base64.decodeBase64(publicKeyBase64));
            byte[] encryptBytesToSend = encrypt(src, publicKey);
            String encryptBytesBase64 = Base64.encodeBase64String(encryptBytesToSend);
            System.out.println("公钥加密---send:" + encryptBytesBase64);
            //2.公钥加密，私钥解密----解密
            byte[] encryptBytesReceived = Base64.decodeBase64(encryptBytesBase64);
            PrivateKey privateKey = getPrivateKey(Base64.decodeBase64(privateKeyBase64));
            String result = decrypt(encryptBytesReceived, privateKey);
            System.out.println("私钥解密---receive:" + result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
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
}
