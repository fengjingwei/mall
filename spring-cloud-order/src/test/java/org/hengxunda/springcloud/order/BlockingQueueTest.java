package org.hengxunda.springcloud.order;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class BlockingQueueTest {

    /*ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
    LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
    PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
    DelayQueue：一个使用优先级队列实现的无界阻塞队列。
    SynchronousQueue：一个不存储元素的阻塞队列。
    LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
    LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。*/

    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100);

    //private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100);

    //private static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(100);

    //private static BlockingQueue<Integer> blockingQueue = new PriorityBlockingQueue<>(100);

    private static Random random = new Random();

    public static void main(String[] args) {
        ScheduledExecutorService product = Executors.newScheduledThreadPool(1);

        product.scheduleAtFixedRate(() -> {
            int value = random.nextInt(101);
            try {
                blockingQueue.offer(value); //offer()方法就是往队列的尾部设置值
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, 200, TimeUnit.MILLISECONDS); //每100毫秒执行线程

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("开始取值");
                    List<Integer> list = Lists.newLinkedList();
                    blockingQueue.drainTo(list);  //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                    list.forEach(System.out::println);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
