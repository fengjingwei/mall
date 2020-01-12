package org.hengxunda.springcloud.order.disruptor;

public class Main {

    public static void main(String[] args) throws Exception {
        DisruptorManager.start(new DataEventHandler());
        for (long l = 1; true; l++) {
            DisruptorManager.publishEvent(l);
            Thread.sleep(1000);
        }
    }
}
