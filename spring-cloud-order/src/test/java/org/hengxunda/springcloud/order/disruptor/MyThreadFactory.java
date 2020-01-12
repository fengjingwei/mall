package org.hengxunda.springcloud.order.disruptor;

import org.springframework.lang.NonNull;

import java.text.MessageFormat;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class MyThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("log");

    private static AtomicBoolean daemon = null;

    private final String namePrefix;

    private MyThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = namePrefix;
        MyThreadFactory.daemon = new AtomicBoolean(daemon);
    }

    public static ThreadFactory create(final String namePrefix, final boolean daemon) {
        return new MyThreadFactory(namePrefix, daemon);
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(THREAD_GROUP, runnable, MessageFormat.format("{0}-{1}-{2}", THREAD_GROUP.getName(), namePrefix, THREAD_NUMBER.getAndIncrement()));
        thread.setDaemon(daemon.get());
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
