package org.hengxunda.springcloud.order.thread.c1;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private static final int SLEEP_TIME = 1000;
    private BlockingQueue<Data> queue;

    Consumer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id :" + Thread.currentThread().getId());
        Random r = new Random();
        try {
            while (true) {
                Data data = queue.take();
                System.out.println(MessageFormat.format("get data value from queue:{0}", data.getData()));
                Thread.sleep(r.nextInt(SLEEP_TIME));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
