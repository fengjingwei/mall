package org.hengxunda.springcloud.order.thread.c1;

import org.hengxunda.springcloud.order.thread.c3.ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Data> queue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        ThreadPool service = ThreadPool.getThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
        Thread.sleep(10000);
        p1.stop();
        p2.stop();
        p3.stop();
        Thread.sleep(3000);
        service.shutdown();
    }
}
