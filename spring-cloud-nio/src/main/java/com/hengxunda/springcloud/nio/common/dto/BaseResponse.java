package com.hengxunda.springcloud.nio.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -7836909157838807185L;

    private int code;

    private String msg;
}
