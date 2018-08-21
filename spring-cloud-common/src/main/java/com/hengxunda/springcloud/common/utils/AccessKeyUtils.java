package com.hengxunda.springcloud.common.utils;

import com.hengxunda.springcloud.common.security.coder.Digests;
import org.apache.commons.codec.CharEncoding;

import java.io.UnsupportedEncodingException;

public abstract class AccessKeyUtils {

    public static String encode(String text, String nonce, String token) {
        byte[] bytes = null;
        try {
            bytes = Digests.sha1(text.getBytes(CharEncoding.UTF_8), token.getBytes());
            bytes = Digests.sha1(bytes, nonce.getBytes(CharEncoding.UTF_8));
        } catch (UnsupportedEncodingException e) {

        }
        return StringUtils.toHex(bytes);
    }

    public static void main(String[] args) {
        System.out.println(encode("6", "www", "!#%&(@$^*)"));
    }
}
