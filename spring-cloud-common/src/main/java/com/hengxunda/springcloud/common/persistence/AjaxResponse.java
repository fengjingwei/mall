package com.hengxunda.springcloud.common.persistence;

import com.hengxunda.springcloud.common.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AjaxResponse implements Serializable {

    private static final long serialVersionUID = -2792556188993845048L;

    private int code;

    private String message;

    private Object data;

    public static AjaxResponse success() {
        return success("");
    }

    public static AjaxResponse success(String msg) {
        return success(msg, null);
    }

    public static AjaxResponse success(Object data) {
        return success(null, data);
    }

    public static AjaxResponse success(String msg, Object data) {
        return get(ErrorCodeEnum.SUCCESS.code(), msg, data);
    }

    public static AjaxResponse error(String msg) {
        return error(ErrorCodeEnum.ERROR.code(), msg);
    }

    public static AjaxResponse error(int code, String msg) {
        return get(code, msg, null);
    }

    public static AjaxResponse get(int code, String msg, Object data) {
        return new AjaxResponse(code, msg, data);
    }

}
