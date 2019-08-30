package com.hengxunda.springcloud.nio.common.handler;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import io.netty.channel.ChannelHandlerContext;

public interface Handlebars {

    /**
     * 初始化将自己加入到HandlerMapper中
     */
    void init();

    /**
     * 消息编号
     *
     * @return
     */
    int msgNo();

    /**
     * 接收并处理请求消息
     *
     * @param ctx
     * @param request
     */
    void process(ChannelHandlerContext ctx, BaseMessage request);
}
