package com.hengxunda.springcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements HttpStatus {

    SUCCESS(SC_OK, "success"),

    ERROR(SC_INTERNAL_SERVER_ERROR, "error");

    private final int code;

    private final String msg;

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
