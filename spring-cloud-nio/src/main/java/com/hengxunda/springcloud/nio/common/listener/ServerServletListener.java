package com.hengxunda.springcloud.nio.common.listener;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hengxunda.springcloud.nio.common.netty.codec.PolicyHandler;
import com.hengxunda.springcloud.nio.common.netty.server.NioServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.*;

@Log4j2
@Configuration
public class ServerServletListener implements ServletContextListener {

    private static final int MAX_THREAD = NettyRuntime.availableProcessors() << 1;
    private final int inetPort = 843;
    private Channel serverChannel;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("mall-nio-pool-%d").build();
            ExecutorService pool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 900000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

            pool.execute(() -> {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .option(ChannelOption.SO_REUSEADDR, true)
                            .childHandler(new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    ch.pipeline().addLast("lineBasedFrameDecoder", new LineBasedFrameDecoder(128));
                                    ch.pipeline().addLast("stringDecoder", new StringDecoder());
                                    ch.pipeline().addLast("stringEncoder", new StringEncoder());
                                    ch.pipeline().addLast("policyHandler", new PolicyHandler());
                                }
                            });
                    serverChannel = b.bind(inetPort).sync().channel();
                    log.info("开启{}端口成功", inetPort);
                    serverChannel.closeFuture().sync();
                } catch (Exception e) {
                    log.error("开启{}端口失败", inetPort, e);
                } finally {
                    bossGroup.shutdownGracefully();
                }
            });

            pool.execute(() -> NioServer.getInstance().run());

            pool.shutdown();
            log.info("start nio server success");
        } catch (Exception e) {
            log.error("start nio server failure", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            serverChannel.close();
            NioServer.getInstance().shutdown();
            event.getServletContext().log("shutdown nio server success");
        } catch (Exception e) {
            event.getServletContext().log("shutdown nio server failure", e);
        }
    }
}
