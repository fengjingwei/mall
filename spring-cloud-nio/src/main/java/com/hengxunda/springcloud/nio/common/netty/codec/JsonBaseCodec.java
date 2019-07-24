package com.hengxunda.springcloud.nio.common.netty.codec;

import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class JsonBaseCodec extends MessageToMessageCodec<String, BaseMessage> {

    /**
     * 占位对象
     */
    private static final Object PLACEHOLDER_OBJ = new Object();

    private static final int MSG_TYPE_LENGTH = 1;

    private static final int MSG_NO_LENGTH = 4;

    private static final int REQUEST = 1;

    private static final int RESPONSE = 2;

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) {
        if (msg.length() < MSG_TYPE_LENGTH + MSG_NO_LENGTH) {
            log.error("接收消息格式错误");
            sendExceptionResponse(ctx);
            return;
        }

        log.info("服务端收到 : {}", msg);
        BaseMessage receivedMsg;
        try {
            String jsonMsg = StringUtils.substring(msg, 5);
            receivedMsg = FastJsonUtils.parseObject(jsonMsg, BaseMessage.class);
            receivedMsg.setMsgType(Integer.valueOf(StringUtils.substring(msg, 0, 1)));
            receivedMsg.setMsgNo(Integer.valueOf(StringUtils.substring(msg, 1, 5)));
            out.add(receivedMsg);
        } catch (Exception e) {
            log.error("消息解析出错, receive msg : {}", msg);
            log.error("异常信息为:", e);
            sendExceptionResponse(ctx);
        }
    }

    private void sendExceptionResponse(ChannelHandlerContext ctx) {
        BaseMessage response = new BaseMessage();
        response.setMsgType(RESPONSE);
        response.setMsgNo(MsgNoEnum.Sys.BAD_REQUEST.getCode());
        response.setStatusCode(BaseMessage.StatusCodeEnum.BAD_REQUEST);
        response.setMessage("请求格式错误");
        ctx.channel().writeAndFlush(response);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, List<Object> out) {
        try {
            initResponse(msg);
            StringBuilder sb = new StringBuilder();
            sb.append(msg.getMsgType())
                    .append(StringUtils.leftPad(Integer.toString(msg.getMsgNo()), 4, '0'))
                    .append(FastJsonUtils.toJSONString(msg));
            log.info("服务端发送 : {}", sb.toString());
            out.add(sb.toString());
        } catch (Exception e) {
            log.error("消息序列化为json串出错,异常信息为:", e);
        }
    }

    private void initResponse(BaseMessage response) {
        response.setMsgType(RESPONSE);
        if (response.getStatusCode() == null) {
            response.setStatusCode(BaseMessage.StatusCodeEnum.OK);
        }

        Object body = response.getBody();
        boolean flag = body instanceof String && StringUtils.isBlank((String) body);
        if (body == null || flag) {
            response.setBody(PLACEHOLDER_OBJ);
        }
    }

}
