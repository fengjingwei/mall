package com.hengxunda.springcloud.nio.common.handler;

import io.netty.channel.Channel;

public interface HandlerListener {

    void notice(Channel channel);
}
