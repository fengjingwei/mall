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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class NioServer {

    private static final NioServer INSTANCE = new NioServer();

    public static final NioServer getInstance() {
        return INSTANCE;
    }

    private Channel serverChannel;

    private static List<Integer> ports;

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

            for (Integer port : ports) {
                serverChannel = b.bind(port).sync().channel();
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

    private static void initPorts() {
        ports = Stream.of(9081, 110).collect(Collectors.toList());
    }

}
