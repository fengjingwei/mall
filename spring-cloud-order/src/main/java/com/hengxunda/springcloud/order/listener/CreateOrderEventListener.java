package com.hengxunda.springcloud.order.listener;

import com.google.common.eventbus.Subscribe;
import com.hengxunda.springcloud.common.event.publisher.DomainEventPublisher;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.events.CreateOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateOrderEventListener {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @PostConstruct
    protected void register() {
        domainEventPublisher.register(this);
    }

    @Subscribe
    protected void onEventListener(CreateOrderEvent event) {
        Order order = event.getOrder();
        System.out.println("order = " + order);
        // save event log
    }
}