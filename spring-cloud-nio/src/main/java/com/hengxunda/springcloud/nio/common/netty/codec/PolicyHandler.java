package com.hengxunda.springcloud.nio.common.netty.codec;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public final class PolicyHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String policy = "<cross-domain-policy><allow-access-from domain='*' to-ports='*' /></cross-domain-policy>\0";
        ctx.channel().writeAndFlush(policy);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) {

    }
}
