package com.hengxunda.springcloud.nio.common.netty.server;

import com.google.common.base.Joiner;
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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@NoArgsConstructor
public class NioServer {

    private static final NioServer INSTANCE = new NioServer();
    private static List<Integer> ports;
    private Channel serverChannel;

    public static NioServer getInstance() {
        return INSTANCE;
    }

    private static void initPorts() {
        ports = Stream.of(9080, 9081).collect(Collectors.toList());
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            initPorts();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_REUSEADDR, true)
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
            log.info("开启{}端口成功", Joiner.on(",").join(ports));
            log.info("服务端开启,等待客户端连接...");
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            log.error("开启{}端口失败", e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        serverChannel.close();
    }
}
