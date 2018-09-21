package com.hengxunda.springcloud.nio.handlers.chat;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.common.netty.RoomChannelContainer;
import com.hengxunda.springcloud.nio.handlers.chat.dto.PrivateChatRequest;
import com.hengxunda.springcloud.nio.handlers.chat.dto.PrivateChatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrivateChatHandler extends AbstractBaseHandler<PrivateChatRequest> {

    @Override
    protected Class<PrivateChatRequest> getRequestBodyClass() {
        return PrivateChatRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, PrivateChatRequest requestBody) {

        PrivateChatResponse response = PrivateChatResponse.builder()
                .roomId(requestBody.getRoomId())
                .content(requestBody.getContent())
                .createTime(requestBody.getCreateTime())
                .build();

        return response;
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Chat.CHAT_P2P.getCode();
    }

    @Override
    protected void sendNotification(Channel channel, PrivateChatRequest requestBody, Object responseBody) {
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.Chat.CHAT_P2P_BROADCAST.getCode());
        message.setBody(requestBody);
        channel = RoomChannelContainer.roomOnlineMaps.get(requestBody.getRoomId()).get(requestBody.getReceivedId());
        super.sendToUser(channel, message);

    }
}
