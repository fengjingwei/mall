package com.hengxunda.springcloud.nio.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface MsgNoEnum {

    @Getter
    @AllArgsConstructor
    enum Sys {

        KEEP_ALIVE(0, "心跳包"),

        BAD_REQUEST(404, "未知请求"),
        ;

        private final Integer code;

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    enum User {

        USER_LOGIN(100, "用户登录消息"),

        USER_ENTER_BROADCAST(1100, "广播用户进入房间消息"),

        USER_LEAVE_BROADCAST(1101, "广播用户离开房间消息"),
        ;

        private final Integer code;

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    enum Room {

        ROOM_INFO(200, "房间信息消息");

        private final Integer code;

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    enum Chat {

        CHAT_P2M(300, "群聊消息"),

        CHAT_P2M_BROADCAST(1300, "广播群聊消息"),

        CHAT_P2P(310, "私聊消息"),

        CHAT_P2P_BROADCAST(1310, "广播私聊消息"),
        ;

        private final Integer code;

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    enum Live {

        LIVE_START(400, "开启直播消息"),

        LIVE_STOP(401, "关闭直播消息"),

        LIVE_START_BROADCAST(1400, "广播开启直播消息"),

        LIVE_STOP_BROADCAST(1401, "广播关闭直播消息"),

        LIVE_ADDRESS(402, "直播推流地址消息"),

        LIVE_GET_STREAM(403, "获取某个直播流信息消息"),
        ;

        private final Integer code;

        private final String msg;
    }
}
