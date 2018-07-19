package zhyu.common.security;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {
    public static class Keys {
        public Keys(String publicKey, String privateKey) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        }
        private String publicKey;
        private String privateKey;

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }
    public static final String ALGORITHM_RSA = "RSA";
    public static final String ALGORITHM_SIGN = "MD5withRSA";
    public static void main(String[] args) throws Exception {
        // 生成公钥和私钥
        Keys keys = generatorKeyPair();
        String source = "我是程序猿！";
        System.out.println("加密前的数----------------公钥加密，私钥解密------------------------------");
        // 公钥加密据：\r\n" + source);
        //        System.out.println("----------
        String target = encryptionByPublicKey(source,keys.getPublicKey());
        // 私钥解密
        decryptionByPrivateKey(target,keys.getPrivateKey());

        System.out.println
                ("--------------------------私钥加密并且签名，公钥验证签名并且解密------------------------------");
        // 私钥加密
        target = encryptionByPrivateKey(source, keys.getPrivateKey());
        // 签名
        String sign = signByPrivateKey(target, keys.getPrivateKey());
        // 验证签名
        verifyByPublicKey(target, sign, keys.getPublicKey());
        // 公钥解密
        decryptionByPublicKey(target, keys.getPublicKey());

    }

    /**
     * 生成密钥对
     * @throws Exception
     */
    public static Keys generatorKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] keyBs = rsaPublicKey.getEncoded();
        String publicKey = encodeBase64(keyBs);
        System.out.println("生成的公钥：\r\n" + publicKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        keyBs = rsaPrivateKey.getEncoded();
        String privateKey = encodeBase64(keyBs);
        System.out.println("生成的私钥：\r\n" + privateKey);
        Keys keys  = new Keys(publicKey, privateKey);
        return keys;
    }

    /**
     * 获取公钥
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String publickey) throws Exception {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec
                (decodeBase64(publickey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 获取私钥
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String privateKey) throws
            Exception {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 公钥加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptionByPublicKey(String source,String publickey)
            throws Exception{
        PublicKey publicKey = getPublicKey(publickey);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
        System.out.println("公钥加密后的数据：\r\n" + target);
        return target;
    }

    /**
     * 公钥解密
     * @param target
     * @throws Exception
     */
    public static void decryptionByPublicKey(String target, String publickey) throws Exception{
        PublicKey publicKey = getPublicKey(publickey);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
        System.out.println("公钥解密后的数据：\r\n" + source);
    }

    /**
     * 公钥验证签名
     * @return
     * @throws Exception
     */
    public static void verifyByPublicKey(String target, String sign, String
            publickey)
            throws
            Exception{
        PublicKey publicKey = getPublicKey(publickey);
        Signature signature = Signature.getInstance(ALGORITHM_SIGN);
        signature.initVerify(publicKey);
        signature.update(target.getBytes("UTF-8"));
        if (signature.verify(decodeBase64(sign))) {
            System.out.println("签名正确！");
        } else {
            System.out.println("签名错误！");
        }
    }

    /**
     * 私钥加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptionByPrivateKey(String source, String
            privatekey)
            throws
            Exception {
        PrivateKey privateKey = getPrivateKey(privatekey);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
        System.out.println("私钥加密后的数据：\r\n" + target);
        return target;
    }

    /**
     * 私钥解密
     * @param target
     * @throws Exception
     */
    public static void decryptionByPrivateKey(String target, String privatekey)
            throws
            Exception {
        PrivateKey privateKey = getPrivateKey(privatekey);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
        System.out.println("私钥解密后的数据：\r\n" + source);
    }

    /**
     * 私钥签名
     * @param target
     * @return
     * @throws Exception
     */
    public static String signByPrivateKey(String target,String privatekey)
            throws Exception{
        PrivateKey privateKey = getPrivateKey(privatekey);
        Signature signature = Signature.getInstance(ALGORITHM_SIGN);
        signature.initSign(privateKey);
        signature.update(target.getBytes("UTF-8"));
        String sign = encodeBase64(signature.sign());
        System.out.println("生成的签名：\r\n" + sign);
        return sign;
    }

    /**
     * base64编码
     * @param source
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[] source) throws Exception{
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

    /**
     * Base64解码
     * @param target
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String target) throws Exception{
        return Base64.decodeBase64(target.getBytes("UTF-8"));
    }
}