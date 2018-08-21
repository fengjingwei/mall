package com.hengxunda.springcloud.common.exception;

import com.hengxunda.springcloud.common.enums.ErrorCodeEnum;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceException extends BaseException {

    @Override
    protected ErrorCodeEnum code() {
        return null;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
