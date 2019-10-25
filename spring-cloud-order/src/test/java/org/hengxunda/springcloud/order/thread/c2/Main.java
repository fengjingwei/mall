package org.hengxunda.springcloud.order.thread.c2;

import org.hengxunda.springcloud.order.thread.c3.ThreadPool;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Data> queue = new ArrayList<>();
        int length = 15;
        Producer p1 = new Producer(queue, length);
        Producer p2 = new Producer(queue, length);
        Producer p3 = new Producer(queue, length);
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
        service.shutdown();
    }
}
