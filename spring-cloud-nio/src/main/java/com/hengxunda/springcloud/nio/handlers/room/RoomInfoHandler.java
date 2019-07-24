package com.hengxunda.springcloud.nio.handlers.room;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.dto.EmptyRequest;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.handlers.room.dto.RoomInfoResponse;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RoomInfoHandler extends AbstractBaseHandler<EmptyRequest> {

    @Override
    protected Class<EmptyRequest> getRequestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        log.info("roomId : {}", roomId);
        return new RoomInfoResponse(roomId);
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Room.ROOM_INFO.getCode();
    }
}
