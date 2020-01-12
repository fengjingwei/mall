package org.hengxunda.springcloud.order.thread.c1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    public static void main(String[] args) {
        BlockingQueue<Data> queue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(p3).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();
    }
}