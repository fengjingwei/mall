package org.hengxunda.springcloud.order.disruptor;

public class DataEventConsumer {

    public DataEventConsumer(DataEvent event) {

        System.out.println("event : " + event.getValue());
    }
}
