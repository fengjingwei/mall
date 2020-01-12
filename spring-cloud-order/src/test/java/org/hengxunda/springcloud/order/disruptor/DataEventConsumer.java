package org.hengxunda.springcloud.order.disruptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataEventConsumer {

    public DataEventConsumer(DataEvent event) {
        log.info("消费者消费 : {}", event.getValue());
    }
}
