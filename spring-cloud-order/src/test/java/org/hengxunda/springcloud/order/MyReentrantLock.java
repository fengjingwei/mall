package org.hengxunda.springcloud.order;

/**
 * 可重入锁
 */
public class MyReentrantLock {

    private boolean isLocked = false;

    private Thread lockedBy = null;

    private int lockedCount = 0;

    private synchronized void lock() throws InterruptedException {
        final Thread thread = Thread.currentThread();
        while (isLocked && lockedBy != thread) {
            wait();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }

    private synchronized void unlock() {
        if (Thread.currentThread() == this.lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }

    public static class TestMyReentrantLock {

        MyReentrantLock myReentrantLock = new MyReentrantLock();

        public static void main(String[] args) {
            try {
                new TestMyReentrantLock().execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            myReentrantLock.lock();
            doSomething();
            myReentrantLock.unlock();
        }

        private void doSomething() throws InterruptedException {
            myReentrantLock.lock();
            System.out.println("执行业务逻辑操作...");
            myReentrantLock.unlock();
        }
    }
}
