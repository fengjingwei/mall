package com.hengxunda.springcloud.nio.handlers.live;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.dto.EmptyRequest;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveStopBroadcast;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveUtils;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LiveStopHandler extends AbstractBaseHandler<EmptyRequest> {

    @Override
    protected Class<EmptyRequest> getRequestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {

        return null;
    }

    @Override
    protected void sendNotification(Channel channel, EmptyRequest requestBody, Object responseBody) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);
        if (Objects.nonNull(stream)) {
            LiveUtils.remove(roomId);

            LVBChannel liveInfo = stream.getLiveInfo();
            LiveStopBroadcast broadcast = LiveStopBroadcast.builder()
                    .channelId(liveInfo.getChannelId())
                    .type(stream.getType())
                    .userId(stream.getUserId())
                    .build();

            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.Live.LIVE_STOP_BROADCAST.getCode());
            message.setBody(broadcast);
            super.sendToRoom(channel, message);
        }
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Live.LIVE_STOP.getCode();
    }
}
