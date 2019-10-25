package org.hengxunda.springcloud.order.thread.c3;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Main {

    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors();

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").setDaemon(true).build();

    /**
     * corePoolSize     线程池中核心线程的数量
     * maximumPoolSize  线程池中最大线程数量 = corePoolSize(核心线程数) + noCorePoolSize(非核心线程数)
     * keepAliveTime    非核心线程的超时时长
     * unit             第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
     * workQueue        线程池中的任务队列，该队列主要用来存储已经被提交但是尚未执行的任务
     * threadFactory    为线程池提供创建新线程的功能
     * handler          拒绝策略，当线程无法执行新任务时（一般是由于线程池中的线程数量已经达到最大数或者线程池关闭导致的），默认情况下，当线程池无法处理新线程时，会抛出一个RejectedExecutionException。
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService s1 = Executors.newCachedThreadPool();
        s1.execute(() -> System.out.println("s1"));
        s1.shutdown();

        ExecutorService s2 = Executors.newScheduledThreadPool(0b1010);
        Future<Integer> future = s2.submit(() -> {
            if (MAX_THREAD == 0b100) {
                return MAX_THREAD << 0b1;
            }
            return MAX_THREAD;
        });
        s2.shutdown();
        System.out.println("future get = " + future.get() + ", future isDone = " + future.isDone());

        ExecutorService s3 = new ThreadPoolExecutor(future.get(), future.get(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        s3.execute(() -> System.out.println("s3"));
        s3.shutdown();

        ThreadPool s4 = ThreadPool.getThreadPool();
        s4.execute(() -> System.out.println("s4"));
        s4.shutdown();
    }
}
