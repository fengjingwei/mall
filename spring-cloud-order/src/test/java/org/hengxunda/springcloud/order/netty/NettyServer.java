package org.hengxunda.springcloud.order.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class NettyServer {

    private int port;

    private Channel serverChannel;

    private NettyServer(final int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new NettyServer(9080).start();
    }

    private void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Unpooled.copiedBuffer("$_".getBytes())));
                            pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
                            pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            serverChannel = b.bind(port).sync().channel();
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }
}
