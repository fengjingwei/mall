package org.hengxunda.springcloud.order.thread.c2;

import java.util.List;
import java.util.Random;

public class Producer implements Runnable {

    private final List<Data> queue;

    private int length;

    Producer(List<Data> queue, int length) {
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Random r = new Random();
                long temp = r.nextInt(100);
                Data data = new Data();
                data.set(temp);
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName() + " - " + thread.getId() + " 生产了：" + temp);
                synchronized (queue) {
                    if (queue.size() >= length) {
                        queue.notifyAll();
                        queue.wait();
                    } else {
                        queue.add(data);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
