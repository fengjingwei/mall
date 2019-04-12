package org.hengxunda.springcloud.order;

import java.util.concurrent.locks.Lock;

public class ReentrantLock implements Runnable {

    private static final Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread1 = new Thread(reentrantLock);
        Thread thread2 = new Thread(reentrantLock);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i = " + i);
    }

    @Override
    public void run() {
        for (int index = 1000; index > 0; index--) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }
}
