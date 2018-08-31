package org.hengxunda.springcloud.order.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;

public class EventTranslator implements EventTranslatorOneArg<DataEvent, Long> {

    @Override
    public void translateTo(DataEvent event, long sequence, Long arg0) {
        event.setValue(arg0);
    }
}
