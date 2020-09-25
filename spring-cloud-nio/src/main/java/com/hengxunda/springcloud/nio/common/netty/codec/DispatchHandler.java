package com.hengxunda.springcloud.nio.common.netty.codec;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.handler.MapperHandler;
import com.hengxunda.springcloud.nio.common.handler.SocketCloseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Optional;

public final class DispatchHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) {
        Optional.ofNullable(MapperHandler.getHandler(msg.getMsgNo())).ifPresent(handler -> handler.process(ctx, msg));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Optional.ofNullable(MapperHandler.getHandler(SocketCloseHandler.CLOSE_MSG_NO)).ifPresent(handler -> handler.process(ctx, null));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
