package com.hengxunda.springcloud.common.exception;

import com.hengxunda.springcloud.common.enums.StatusCodeEnum;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = -6821564343636571746L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected abstract StatusCodeEnum code();
}
