package com.hengxunda.springcloud.order.command.bus;

import com.hengxunda.springcloud.common.event.publisher.DomainEventPublisher;
import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.common.persistence.CommandBus;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.events.CreateOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandBus implements CommandBus {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Override
    public <T> Object dispatch(Command command, T model) {
        Order order = (Order) command.execute(model);
        domainEventPublisher.publish(new CreateOrderEvent(order));
        return order;
    }
}
