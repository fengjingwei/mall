package org.hengxunda.springcloud.order.disruptor;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataEventHandler implements EventHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) {
        new DataEventConsumer(dataEvent);
    }
}
