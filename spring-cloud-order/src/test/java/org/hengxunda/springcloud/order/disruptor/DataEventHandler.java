package org.hengxunda.springcloud.order.disruptor;


import com.lmax.disruptor.EventHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataEventHandler implements EventHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) {
        new DataEventConsumer(dataEvent);
    }
}
