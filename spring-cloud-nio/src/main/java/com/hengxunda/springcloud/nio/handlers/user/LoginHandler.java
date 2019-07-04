package com.hengxunda.springcloud.nio.handlers.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hengxunda.springcloud.common.security.jwt.AccountJWT;
import com.hengxunda.springcloud.common.utils.DateUtils;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.common.handler.HandlerListener;
import com.hengxunda.springcloud.nio.common.netty.RoomChannelContainer;
import com.hengxunda.springcloud.nio.handlers.user.dto.LoginRequest;
import com.hengxunda.springcloud.nio.handlers.user.dto.LoginResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class LoginHandler extends AbstractBaseHandler<LoginRequest> {

    private List<HandlerListener> handlerListeners = Lists.newArrayList();

    @Override
    public int msgNo() {
        return MsgNoEnum.User.USER_LOGIN.getCode();
    }

    @Override
    protected Class<LoginRequest> getRequestBodyClass() {
        return LoginRequest.class;
    }

    @Override
    protected boolean isNeedCheckAuth() {
        return false;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LoginRequest requestBody) {
        enterRoom(requestBody, channel);

        LoginResponse response = LoginResponse.builder()
                .userId(requestBody.getUserId())
                .build();

        RoomChannelContainer.addChannel(channel);
        Map<Long, Channel> onlineMaps = Maps.newHashMap();
        onlineMaps.put(requestBody.getUserId(), channel);
        RoomChannelContainer.ROOM_ONLINE_MAPS.put(requestBody.getRoomId(), onlineMaps);
        return response;
    }

    public void addHandlerListener(HandlerListener listener) {
        handlerListeners.add(listener);
    }

    private void enterRoom(LoginRequest requestBody, Channel channel) {
        UserUtils.setRoomId(channel, requestBody.getRoomId());
        UserUtils.setMode(channel, requestBody.getMode());
        UserUtils.setUser(channel, new AccountJWT(requestBody.getUserId()));
        UserUtils.setCreateTime(channel, DateUtils.getLocalDateTime());
    }

    @Override
    protected void sendNotification(Channel channel, LoginRequest request, Object responseBody) {
        sendEnterRoomNotification(channel);
        handlerListeners.forEach(listener -> listener.notice(channel));
    }

    private void sendEnterRoomNotification(Channel channel) {
        AccountJWT accountJWT = UserUtils.getUser(channel);
        if (RoomChannelContainer.isOtherChannelInRoom(channel)) {
            log.error("该用户已经有其它端在线,不发送进入房间通知, userId : {}", accountJWT.getId());
            return;
        }
        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.User.USER_ENTER_BROADCAST.getCode());
        message.setBody(accountJWT);
        super.sendToRoomExcludeSelf(channel, message);
    }

    public static final class UserUtils {

        private static final AttributeKey<String> ROOM_ID = AttributeKey.valueOf("roomId");

        private static final AttributeKey<Integer> MODE = AttributeKey.valueOf("mode");

        private static final AttributeKey<LocalDateTime> CREATE_TIME = AttributeKey.valueOf("createTime");

        private static final AttributeKey<AccountJWT> USER = AttributeKey.valueOf("user");

        private static void setRoomId(Channel channel, String roomId) {
            channel.attr(ROOM_ID).set(roomId);
        }

        public static String getRoomId(Channel channel) {
            return channel.attr(ROOM_ID).get();
        }

        private static void setMode(Channel channel, Integer id) {
            channel.attr(MODE).set(id);
        }

        public static Integer getMode(Channel channel) {
            return channel.attr(MODE).get();
        }

        private static void setCreateTime(Channel channel, LocalDateTime localDateTime) {
            channel.attr(CREATE_TIME).set(localDateTime);
        }

        public static LocalDateTime getCreateTime(Channel channel) {
            return channel.attr(CREATE_TIME).get();
        }

        public static void setUser(Channel channel, AccountJWT user) {
            channel.attr(USER).set(user);
        }

        public static AccountJWT getUser(Channel channel) {
            return channel.attr(USER).get();
        }

        public static Long getUserId(Channel channel) {
            AccountJWT user = getUser(channel);
            return Objects.nonNull(user) ? user.getId() : null;
        }
    }

}
