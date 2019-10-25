package org.hengxunda.springcloud.order.thread.c2;

import java.util.List;

public class Consumer implements Runnable {

    private final List<Data> queue;

    public Consumer(List<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                Data data;
                synchronized (queue) {
                    if (queue.size() == 0) {
                        queue.wait();
                        queue.notifyAll();
                    }
                    data = queue.remove(0);
                }
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName() + " - " + thread.getId() + " 消费了:" + data.get() + " result:" + (data.get() * data.get()));
                System.out.println("队列中的长度:" + queue.size());
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
