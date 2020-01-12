package org.hengxunda.springcloud.order.disruptor;

import com.lmax.disruptor.EventHandler;

public class DataEventHandler implements EventHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) {
        new DataEventConsumer(dataEvent);
    }
}
