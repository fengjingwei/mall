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
    private static final AtomicLong DATA = new AtomicLong();
    private static final AtomicInteger INDEX = new AtomicInteger(1);
    private static Executor executor;
    private static Disruptor<DataEvent> disruptor;
    private static RingBuffer<DataEvent> ringBuffer;

    static void start(EventHandler<DataEvent> eventHandler) {
        disruptor = new Disruptor<>(new DataEventFactory(), RING_BUFFER_SIZE, runnable -> {
            return new Thread(null, runnable, "disruptor-thread-" + INDEX.getAndIncrement());
        }, ProducerType.MULTI, new BlockingWaitStrategy());

        executor = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 900000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(RING_BUFFER_SIZE),
                MyThreadFactory.create("disruptor", false),
                new ThreadPoolExecutor.AbortPolicy());

        ringBuffer = disruptor.getRingBuffer();

        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("放入队列中数据编号{},队列剩余空间{}", DATA.get(), ringBuffer.remainingCapacity());
            }
        }, new Date(), 30 * 1000);
    }

    static void publishEvent(final long message) {
        executor.execute(() -> {
            if (DATA.get() == Long.MAX_VALUE) {
                DATA.set(0L);
            }
            final RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
            long next = ringBuffer.next();
            try {
                ringBuffer.get(next).setValue(message);
                final long value = DATA.incrementAndGet();
                log.info("生产者生产 : {}", value);
            } catch (Exception e) {
                log.error("生产者数据[ {} ]出现异常 {}", message, e.getStackTrace());
            } finally {
                ringBuffer.publish(next);
            }
        });
    }

    public static void destroy() {
        disruptor.shutdown();
    }
}
