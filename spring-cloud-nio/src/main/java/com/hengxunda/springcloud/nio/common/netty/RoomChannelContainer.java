package com.hengxunda.springcloud.nio.common.netty;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class RoomChannelContainer {

    private static final Map<String, ChannelGroup> roomGroups = Maps.newConcurrentMap();

    private static final ChannelGroup userGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup visitorGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void addChannel(Channel channel) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        if (Objects.nonNull(roomId)) {
            ChannelGroup group = getGroup(roomId);
            group.add(channel);
        }
        if (isLogin(channel)) {
            userGroup.add(channel);
        } else {
            visitorGroup.add(channel);
        }
    }

    private static ChannelGroup getGroup(String key) {
        ChannelGroup group = roomGroups.get(key);
        if (Objects.isNull(group)) {
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            roomGroups.put(key, group);
        }
        return group;
    }

    public static void removeChannel(Channel channel) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        if (Objects.nonNull(roomId)) {
            ChannelGroup group = roomGroups.get(roomId);
            if (Objects.nonNull(group)) {
                group.remove(channel);
            }
        }

        if (isLogin(channel)) {
            userGroup.remove(channel);
        } else {
            visitorGroup.remove(channel);
        }
    }

    public static List<Channel> getAllChannels(boolean isLogin) {
        ChannelGroup group = isLogin ? userGroup : visitorGroup;
        return newList(group);
    }

    public static List<Channel> getAllChannels(Channel channel) {
        ChannelGroup group = isLogin(channel) ? userGroup : visitorGroup;
        return newList(group);
    }

    public static ChannelGroup getGroupByRoom(String roomId) {
        return roomGroups.get(roomId);
    }

    public static ChannelGroup getGroupByRoom(Channel channel) {
        return getGroupByRoom(LoginHandler.UserUtils.getRoomId(channel));
    }

    public static List<Channel> getChannelsInRoom(String roomId) {
        return newList(roomGroups.get(roomId));
    }

    public static List<Channel> getChannelsInRoom(Channel channel) {
        return getChannelsInRoom(LoginHandler.UserUtils.getRoomId(channel));
    }

    public static boolean isOtherChannelInRoom(Channel channel) {
        return isOtherChannelOnline(channel, getChannelsInRoom(channel));
    }

    private static boolean isOtherChannelOnline(Channel channel, List<Channel> channels) {
        boolean result = false;
        try {
            Long currentUserId = LoginHandler.UserUtils.getUserId(channel);
            if (Objects.nonNull(currentUserId)) {
                for (Channel nioChannel : channels) {
                    if (nioChannel != channel && currentUserId.equals(LoginHandler.UserUtils.getUserId(nioChannel))) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("判断当前用户是否有多端在线失败", e);
        }
        return result;
    }

    private static List<Channel> newList(ChannelGroup group) {
        List<Channel> channels = Lists.newArrayList();
        if (Objects.nonNull(group)) {
            group.iterator().forEachRemaining(channel -> {
                channels.add(channel);
            });
        }
        return channels;
    }

    private static boolean isLogin(Channel channel) {
        return Objects.nonNull(LoginHandler.UserUtils.getUser(channel));
    }
}
