package com.hengxunda.springcloud.nio.common.netty.server;

import com.hengxunda.springcloud.nio.common.netty.codec.DispatchHandler;
import com.hengxunda.springcloud.nio.common.netty.codec.InitialDemuxHandler;
import com.hengxunda.springcloud.nio.common.netty.codec.JsonBaseCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class NioServer {

    private static final NioServer INSTANCE = new NioServer();

    public static final NioServer getInstance() {
        return INSTANCE;
    }

    private Channel serverChannel;

    private static final List<Integer> ports = Arrays.asList(9081, 110);

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            initPorts();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_REUSEADDR, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("initialDemuxHandler", new InitialDemuxHandler());
                            ch.pipeline().addLast("jsonBaseCodec", new JsonBaseCodec());
                            ch.pipeline().addLast("dispatchHandler", new DispatchHandler());
                        }
                    });

            for (Integer integer : ports) {
                serverChannel = b.bind(integer).sync().channel();
            }
            log.info("服务端开启,等待客户端连接...");
            log.info("Listener on port : {}", ports);
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            log.error("nio消息服务启动失败", e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        serverChannel.close();
    }

    private void initPorts() {

    }

}
