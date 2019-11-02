package com.hengxunda.springcloud.common.persistence;

import com.hengxunda.springcloud.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AjaxResponse<T> implements Serializable {

    private static final long serialVersionUID = -2792556188993845048L;

    private int code;

    private String message;

    private T data;

    public static AjaxResponse success() {
        return success("");
    }

    public static AjaxResponse success(String msg) {
        return success(msg, null);
    }

    public static <T> AjaxResponse<T> success(T data) {
        return success(null, data);
    }

    public static AjaxResponse success(String msg, Object data) {
        return get(StatusCodeEnum.SUCCESS.code(), msg, data);
    }

    public static AjaxResponse error(String msg) {
        return error(StatusCodeEnum.ERROR.code(), msg);
    }

    public static AjaxResponse error(int code, String msg) {
        return get(code, msg, null);
    }

    public static AjaxResponse get(int code, String msg, Object data) {
        return new AjaxResponse(code, msg, data);
    }
}
