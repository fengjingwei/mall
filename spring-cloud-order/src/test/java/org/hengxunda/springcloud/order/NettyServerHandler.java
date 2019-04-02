package org.hengxunda.springcloud.order;

import com.hengxunda.springcloud.common.utils.SnowFlakeUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final String body = (String) msg;
        System.out.println("服务端收到客户端[" + ctx.channel().remoteAddress() + "] -> " + body);
        ctx.writeAndFlush(SnowFlakeUtils.getInstance().getId() + "$_");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
