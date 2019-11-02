package com.hengxunda.springcloud.common.a;

import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.common.utils.StringUtils;

public abstract class Assert {

    public static void state(boolean expression, String msg) {
        if (expression) {
            throw new ServiceException(msg);
        }
    }

    public static void isNull(Object object, String msg) {
        if (object == null) {
            throw new ServiceException(msg);
        }
    }

    public static void isBlank(String text, String msg) {
        if (StringUtils.isBlank(text)) {
            throw new ServiceException(msg);
        }
    }
}
