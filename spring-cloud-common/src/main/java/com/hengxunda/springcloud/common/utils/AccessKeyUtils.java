package com.hengxunda.springcloud.common.utils;

import com.hengxunda.springcloud.common.security.coder.Digests;

import java.nio.charset.StandardCharsets;

public abstract class AccessKeyUtils {

    public static String encode(String text, String nonce, String token) {
        byte[] bytes;
        bytes = Digests.sha1(text.getBytes(StandardCharsets.UTF_8), token.getBytes());
        bytes = Digests.sha1(bytes, nonce.getBytes(StandardCharsets.UTF_8));
        return StringUtils.toHex(bytes);
    }

    public static void main(String[] args) {
        System.out.println(encode("6", "www", "!#%&(@$^*)"));
    }
}
