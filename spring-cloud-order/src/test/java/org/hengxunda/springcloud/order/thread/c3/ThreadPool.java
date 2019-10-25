package org.hengxunda.springcloud.order.thread.c3;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private static ThreadPool threadPool;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
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
                    // 获取处理器数量
                    int cpuNum = Runtime.getRuntime().availableProcessors();
                    // 根据cpu数量,计算出合理的线程并发数
                    int threadNum = (cpuNum << 1) + 1;
                    threadPool = new ThreadPool(threadNum, threadNum, 0L);
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
