package com.hengxunda.springcloud.order.command.bus;

import com.hengxunda.springcloud.common.event.publisher.DomainEventPublisher;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.common.persistence.CommandBus;
import com.hengxunda.springcloud.order.events.CancelOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderCommandBus implements CommandBus {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public <T> Object dispatch(Command command, T model) {
        String orderNo = (String) command.execute(model);
        domainEventPublisher.post(new CancelOrderEvent(orderNo));
        return orderNo;
    }
}
