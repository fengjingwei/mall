package com.hengxunda.springcloud.common.exception;

import com.hengxunda.springcloud.common.enums.StatusCodeEnum;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceException extends BaseException {

    private static final long serialVersionUID = -5539836153705457277L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected StatusCodeEnum code() {
        return StatusCodeEnum.ERROR;
    }
}
