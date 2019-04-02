package com.hengxunda.springcloud.common.a;

import com.hengxunda.springcloud.common.exception.ServiceException;

public abstract class Assert extends org.springframework.util.Assert {

    public static void check(boolean check, String msg) {
        if (check) {
            throw new ServiceException(msg);
        }
    }
}
