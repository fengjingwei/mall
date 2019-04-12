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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.*;

@Slf4j
@SpringBootConfiguration
public class ServerServletListener implements ServletContextListener {

    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors() << 1;
    private static int inetPort = 843;
    private Channel serverChannel;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("mall-nio-pool-%d").build();
            ExecutorService pool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

            pool.execute(() -> {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_REUSEADDR, true)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    ch.pipeline().addLast(new LineBasedFrameDecoder(128));
                                    ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new StringEncoder());
                                    ch.pipeline().addLast(new PolicyHandler());
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
