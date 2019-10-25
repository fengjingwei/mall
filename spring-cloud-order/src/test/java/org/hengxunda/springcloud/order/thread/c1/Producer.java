package org.hengxunda.springcloud.order.thread.c1;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

    private static final int SLEEP_TIME = 1000;
    private static AtomicInteger count = new AtomicInteger();
    private volatile boolean isRunning = true;
    private BlockingQueue<Data> queue;

    Producer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Data data;
        Random r = new Random();
        System.out.println("start Producer id:" + Thread.currentThread().getId());
        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(SLEEP_TIME));
                data = new Data(count.incrementAndGet());
                System.out.println("push data value to queue:" + data);
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.err.println("push data value to queue error");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
