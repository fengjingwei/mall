package com.hengxunda.springcloud.nio.common.handler;

import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.netty.RoomChannelContainer;
import com.hengxunda.springcloud.nio.common.netty.RoomChannelMatcher;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Log4j2
public abstract class AbstractBaseHandler<R> implements Handleable {

    @PostConstruct
    public void postConstruct() {
        this.init();
    }

    @Override
    public void init() {
        MapperHandler.addHandler(this);
    }

    @Override
    public void process(ChannelHandlerContext ctx, BaseMessage request) {
        Object responseBody;
        R requestBody = null;
        Channel channel = ctx.channel();
        if (isNeedCheckAuth() && notLoggedIn(channel)) {
            channel.close();
            log.error("非登录状态下不能访问此消息接口");
            return;
        }
        try {
            if (request.getBody() != null) {
                requestBody = FastJsonUtils.parseObject((String) request.getBody(), getRequestBodyClass());
                request.setBody(requestBody);
            }
            responseBody = doProcess(channel, request, requestBody);
            if (isNeedResponse()) {
                BaseMessage responseOk = BaseMessage.getResponseOk(request);
                responseOk.setBody(responseBody);
                channel.writeAndFlush(responseOk);
            }
        } catch (Exception e) {
            log.error("处理消息失败, request: {}", request, e);
            if (isNeedResponse()) {
                BaseMessage responseException = BaseMessage.getResponseException(request);
                responseException.setMessage(e.getMessage());
                responseException.setStatusCode(BaseMessage.StatusCodeEnum.BAD_REQUEST);
                channel.writeAndFlush(responseException);
            }
            return;
        }

        sendNotification(channel, requestBody, responseBody);
    }

    protected void sendNotification(Channel channel, R requestBody, Object responseBody) {

    }

    protected final void sendToUser(Channel channel, BaseMessage message) {
        try {
            channel.writeAndFlush(message);
        } catch (Exception ignored) {

        }
    }

    protected final void sendToRoom(Channel channel, BaseMessage message) {
        RoomChannelContainer.getGroupByRoom(channel).writeAndFlush(message);
    }

    protected final void sendToRoomExcludeSelf(Channel channel, BaseMessage message) {
        RoomChannelContainer.getGroupByRoom(channel).writeAndFlush(message, new RoomChannelMatcher(channel));
    }

    private boolean notLoggedIn(Channel channel) {
        return Objects.isNull(LoginHandler.UserUtils.getUser(channel));
    }

    protected boolean isNeedResponse() {
        return true;
    }

    protected boolean isNeedCheckAuth() {
        return true;
    }

    protected abstract Class<R> getRequestBodyClass();

    protected abstract Object doProcess(Channel channel, BaseMessage request, R requestBody);
}
