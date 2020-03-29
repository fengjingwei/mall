package com.hengxunda.springcloud.order.command.bus;

import com.hengxunda.springcloud.common.event.publisher.DomainEventPublisher;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.common.persistence.CommandBus;
import com.hengxunda.springcloud.order.events.PayOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayOrderCommandBus implements CommandBus {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public <T> Object dispatch(Command command, T model) {
        String orderNo = (String) command.execute(model);
        domainEventPublisher.publish(new PayOrderEvent(orderNo));
        return orderNo;
    }
}
