package com.hengxunda.springcloud.nio.common.netty.codec;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.handler.Handlebars;
import com.hengxunda.springcloud.nio.common.handler.MapperHandler;
import com.hengxunda.springcloud.nio.common.handler.SocketCloseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public final class DispatchHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Handlebars handler = MapperHandler.getHandler(SocketCloseHandler.CLOSE_MSG);
        handler.process(ctx, null);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) {
        Handlebars handler = MapperHandler.getHandler(msg.getMsgNo());
        if (handler != null) {
            handler.process(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
