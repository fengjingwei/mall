package com.hengxunda.springcloud.common.security.coder;

import java.security.Security;

/**
 * 加密基类
 */
public abstract class SecurityCoder {

    static {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());
    }
}
