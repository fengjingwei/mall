package org.hengxunda.springcloud.order.thread.c3;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class ThreadPool {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private static final int MAX_THREAD = (Runtime.getRuntime().availableProcessors() << 1) + 1;
    private static volatile ThreadPool threadPool;
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private ExecutorService execute;

    private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public static ThreadPool getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPool(MAX_THREAD, MAX_THREAD, 0L);
                }
            }
        }
        return threadPool;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (execute == null) {
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("pool-" + POOL_NUMBER.getAndIncrement() + "-thread-%d").setDaemon(true).build();
            execute = new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(),
                    threadFactory,
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }
        execute.execute(runnable);
    }

    public void shutdown() {
        if (!execute.isShutdown()) {
            execute.shutdown();
        }
        threadPool = null;
    }
}
