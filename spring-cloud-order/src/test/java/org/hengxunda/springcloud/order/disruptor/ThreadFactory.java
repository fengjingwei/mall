package org.hengxunda.springcloud.order.disruptor;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("log");

    private static volatile boolean daemon;

    private final String namePrefix;

    private ThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = namePrefix;
        ThreadFactory.daemon = daemon;
    }

    public static java.util.concurrent.ThreadFactory create(final String namePrefix, final boolean daemon) {
        return new ThreadFactory(namePrefix, daemon);
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(THREAD_GROUP, r,
                THREAD_GROUP.getName() + "-" + namePrefix + "-" + THREAD_NUMBER.getAndIncrement());
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
