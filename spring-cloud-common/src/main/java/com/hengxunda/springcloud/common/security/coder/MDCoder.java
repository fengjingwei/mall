package com.hengxunda.springcloud.common.security.coder;

import com.hengxunda.springcloud.common.security.Hex;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * MD加密组件
 */
public abstract class MDCoder extends SecurityCoder {

    private static final char[] HEXDIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * MD2加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD2(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD2");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD4加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD4(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD4");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD5加密
     *
     * @param seq
     * @return
     */
    public static String md5(String seq) {
        String digest;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md = md5.digest(seq.getBytes());
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = HEXDIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEXDIGITS[byte0 & 0xf];
            }
            digest = new String(str);
        } catch (Exception e) {
            digest = null;
        }
        return digest;
    }

    public static String md5ToSimple(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * Tiger加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeTiger(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("Tiger");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * TigerHex加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeTigerHex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeTiger(data);
        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * Whirlpool加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeWhirlpool(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("Whirlpool");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * WhirlpoolHex加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeWhirlpoolHex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeWhirlpool(data);
        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * GOST3411加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeGOST3411(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("GOST3411");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * GOST3411Hex加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeGOST3411Hex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeGOST3411(data);
        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }
}
