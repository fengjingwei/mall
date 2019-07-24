package org.hengxunda.springcloud.order.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public class DisruptorManager {

    private static final int RING_BUFFER_SIZE = 1024;

    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors() << 1;

    private static Executor executor;

    private static Disruptor<DataEvent> disruptor;

    private static RingBuffer<DataEvent> ringBuffer;

    private static AtomicLong data = new AtomicLong();

    private static AtomicInteger index = new AtomicInteger(1);

    static void start(EventHandler<DataEvent> eventHandler) {

        disruptor = new Disruptor<>(new DataEventFactory(), RING_BUFFER_SIZE, r -> {
            return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
        }, ProducerType.MULTI, new BlockingWaitStrategy());

        executor = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                ThreadFactory.create("log-disruptor", false),
                new ThreadPoolExecutor.AbortPolicy());

        ringBuffer = disruptor.getRingBuffer();

        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("放入队列中数据编号{},队列剩余空间{}", data.get(), ringBuffer.remainingCapacity());
            }
        }, new Date(), 30 * 1000);
    }

    static void publishEvent(final long message) {
        executor.execute(() -> {
            if (data.get() == Long.MAX_VALUE) {
                data.set(0L);
            }
            final RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
            long next = ringBuffer.next();
            try {
                ringBuffer.get(next).setValue(message);
                data.incrementAndGet();
            } catch (Exception e) {
                log.error("存入数据[ {} ]出现异常 {}", message, e.getStackTrace());
            } finally {
                ringBuffer.publish(next);
            }
        });
    }

    public static void destroy() {
        disruptor.shutdown();
    }

}
