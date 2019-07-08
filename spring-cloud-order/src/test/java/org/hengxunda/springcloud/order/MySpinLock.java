package org.hengxunda.springcloud.order;

/**
 * 不可重入锁(自旋锁)
 */
public class MySpinLock {

    private boolean isLocked = false;

    private synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    private synchronized void unlock() {
        isLocked = false;
        notify();
    }

    public static class TestMySpinLock {

        MySpinLock mySpinLock = new MySpinLock();

        public static void main(String[] args) {
            try {
                new TestMySpinLock().execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void execute() throws InterruptedException {
            mySpinLock.lock();
            doSomething();
            mySpinLock.unlock();
        }

        private void doSomething() throws InterruptedException {
            mySpinLock.lock();
            System.out.println("执行业务逻辑操作...");
            mySpinLock.unlock();
        }
    }
}
