package com.hengxunda.springcloud.nio.handlers.chat;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.handlers.chat.dto.GroupChatRequest;
import com.hengxunda.springcloud.nio.handlers.chat.dto.GroupChatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupChatHandler extends AbstractBaseHandler<GroupChatRequest> {

    @Override
    protected Class<GroupChatRequest> getRequestBodyClass() {
        return GroupChatRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, GroupChatRequest requestBody) {

        GroupChatResponse response = GroupChatResponse.builder()
                .roomId(requestBody.getRoomId())
                .content(requestBody.getContent())
                .createTime(requestBody.getCreateTime())
                .build();

        return response;
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Chat.CHAT_P2M.getCode();
    }

    @Override
    protected void sendNotification(Channel channel, GroupChatRequest requestBody, Object responseBody) {
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.Chat.CHAT_P2M_BROADCAST.getCode());
        message.setBody(requestBody);
        super.sendToRoom(channel, message);
    }
}