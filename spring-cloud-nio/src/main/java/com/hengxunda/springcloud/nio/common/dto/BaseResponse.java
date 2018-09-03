package com.hengxunda.springcloud.nio.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private int code;

    private String msg;
}
