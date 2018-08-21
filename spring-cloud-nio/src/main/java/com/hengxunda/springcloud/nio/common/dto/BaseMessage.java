package com.hengxunda.springcloud.nio.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.hengxunda.springcloud.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = -5984872972282890337L;

    @JSONField(ordinal = 1)
    private String msgId;

    @JSONField(serialize = false)
    private Integer msgType;

    @JSONField(serialize = false)
    private Integer msgNo;

    private Date timestamp;

    private StatusCodeEnum statusCode;

    @JSONField(ordinal = 3)
    private String message;

    @JSONField(ordinal = 2)
    private Object body;

    @JSONField(name = "status", ordinal = 4)
    public int getStatus() {
        return statusCode.getCode();
    }

    @JSONField(serialize = false)
    public StatusCodeEnum getStatusCode() {
        return statusCode;
    }

    @JSONField(deserialize = false)
    public void setStatusCode(StatusCodeEnum statusCode) {
        this.statusCode = statusCode;
    }

    @JSONField(deserialize = false)
    public void setBody(Object body) {
        this.body = body;
    }

    @JSONField(name = "body")
    public void setBody(String body) {
        this.body = body;
    }

    @Getter
    @AllArgsConstructor
    public enum StatusCodeEnum {

        OK(200, "正常"), BAD_REQUEST(400, "请求异常"), SERVER_ERROR(500, "服务器错误");

        private final int code;

        private final String msg;
    }

    public static BaseMessage getResponseException(BaseMessage request) {
        return makeInstance(request, StatusCodeEnum.SERVER_ERROR);
    }

    public static BaseMessage getResponseOk(BaseMessage request) {
        return makeInstance(request, StatusCodeEnum.OK);
    }

    public static BaseMessage getNotification() {
        BaseMessage response = new BaseMessage();
        response.setTimestamp(DateUtils.now());
        response.setMsgId("");
        response.setMsgType(2);
        response.setStatusCode(StatusCodeEnum.OK);
        return response;
    }

    private static BaseMessage makeInstance(BaseMessage request, StatusCodeEnum code) {
        BaseMessage response = new BaseMessage();
        response.setTimestamp(DateUtils.now());
        response.setMsgId(request.getMsgId());
        response.setMsgNo(request.getMsgNo());
        response.setMsgType(2);
        response.setStatusCode(code);
        return response;
    }

}
