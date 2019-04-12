package com.hengxunda.springcloud.nio.common.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomChannelMatcher implements ChannelMatcher {

    private Channel excludeChannel;

    @Override
    public boolean matches(Channel channel) {
        return excludeChannel != channel;
    }
}
