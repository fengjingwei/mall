package com.hengxunda.springcloud.common.exception;

import com.hengxunda.springcloud.common.enums.ErrorCodeEnum;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected abstract ErrorCodeEnum code();

}
