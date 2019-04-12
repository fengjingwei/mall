package com.hengxunda.springcloud.common.security.coder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 3DES加密工具类
 */
public class Des3Coder {

    /**
     * 密钥24
     */
    private final static String SECRET_KEY = "2017755#&neo158144$w@#10";

    /**
     * 向量
     */
    private final static String IV = "01238567";

    /**
     * 加解密统一使用的编码方式
     */
    private final static String ENCODING = "utf-8";

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key desKey;
        DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        desKey = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(ENCODING));
        return Base64.encode(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key desKey;
        DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        desKey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

        return new String(decryptData, ENCODING);
    }

    public static void main(String[] args) {

        try {
            String suString = Des3Coder.encode("123456");
            System.out.println("123456=" + suString);

            String soString = Des3Coder.decode("7+ZTVjuPveyntjFgWOURhg==");
            System.out.println(soString);

            String phoneString = "15016038398 ";
            System.out.println(phoneString.trim());
            String check = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";

            Pattern regex = Pattern.compile(check);

            Matcher matcher = regex.matcher(phoneString.trim());

            boolean flag = matcher.matches();
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
