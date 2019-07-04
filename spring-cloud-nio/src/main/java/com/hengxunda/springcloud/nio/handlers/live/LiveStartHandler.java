package com.hengxunda.springcloud.nio.handlers.live;

import com.hengxunda.springcloud.common.utils.DateUtils;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.common.handler.HandlerListener;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveStartBroadcast;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveStartRequest;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveStartResponse;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveAddressUtils;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveUtils;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveUtils.RoomLiveStream;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LiveStartHandler extends AbstractBaseHandler<LiveStartRequest> implements HandlerListener, InitializingBean {

    @Autowired
    private LoginHandler loginHandler;

    @Override
    protected Class<LiveStartRequest> getRequestBodyClass() {
        return LiveStartRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LiveStartRequest requestBody) {
        LiveStartResponse response = new LiveStartResponse();
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        if (LiveUtils.isPlaying(roomId)) {
            response.setCode(1);
            response.setMsg("此房间已经在直播中");
        } else {
            LVBChannel liveInfo = LiveAddressUtils.get(roomId);
            if (Objects.isNull(liveInfo)) {
                response.setCode(2);
                response.setMsg("请先获取直播地址");
            } else {
                response.setCode(0);
                RoomLiveStream stream = RoomLiveStream.builder()
                        .playing(true)
                        .socketId(channel.id().asLongText())
                        .userId(LoginHandler.UserUtils.getUserId(channel))
                        .roomId(roomId)
                        .liveInfo(liveInfo)
                        .type(requestBody.getType())
                        .playTime(DateUtils.now())
                        .build();
                LiveUtils.put(stream);
            }
        }
        return response;
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Live.LIVE_START.getCode();
    }

    @Override
    protected void sendNotification(Channel channel, LiveStartRequest requestBody, Object responseBody) {
        if (responseBody instanceof LiveStartResponse) {
            LiveStartResponse response = (LiveStartResponse) responseBody;
            if (response.getCode() == 0) {
                String roomId = LoginHandler.UserUtils.getRoomId(channel);
                LiveUtils.RoomLiveStream stream = LiveUtils.get(roomId);

                LiveStartBroadcast broadcast = new LiveStartBroadcast(requestBody);
                broadcast.setDownstream(stream.getLiveInfo());
                broadcast.setPage(1);
                broadcast.setSeek(0);

                BaseMessage message = BaseMessage.getNotification();
                message.setMsgNo(MsgNoEnum.Live.LIVE_START_BROADCAST.getCode());
                message.setBody(broadcast);
                super.sendToRoomExcludeSelf(channel, message);
            }
        }
    }

    @Override
    public void notice(Channel channel) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        RoomLiveStream stream = LiveUtils.get(roomId);
        if (Objects.nonNull(stream)) {
            LiveStartBroadcast broadcast = new LiveStartBroadcast();
            broadcast.setDownstream(stream.getLiveInfo());
            broadcast.setUserId(stream.getUserId());
            broadcast.setType(stream.getType());
            broadcast.setSeek((int) (System.currentTimeMillis() - stream.getPlayTime().getTime()) / 1000);
            broadcast.setPage(1);

            BaseMessage message = BaseMessage.getNotification();
            message.setMsgNo(MsgNoEnum.Live.LIVE_START_BROADCAST.getCode());
            message.setBody(broadcast);
            channel.writeAndFlush(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (Objects.nonNull(loginHandler)) {
            loginHandler.addHandlerListener(this);
        }
    }
}
