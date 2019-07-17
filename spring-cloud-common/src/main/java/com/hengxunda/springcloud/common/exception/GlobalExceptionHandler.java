package com.hengxunda.springcloud.common.exception;

import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public AjaxResponse handleException(ServiceException e) {
        return AjaxResponse.error(e.getMessage());
    }
}