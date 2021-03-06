package com.hengxunda.springcloud.order.listener;

import com.google.common.eventbus.Subscribe;
import com.hengxunda.springcloud.common.event.publisher.DomainEventPublisher;
import com.hengxunda.springcloud.order.events.CancelOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CancelOrderEventListener {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @PostConstruct
    protected void register() {
        domainEventPublisher.register(this);
    }

    @Subscribe
    protected void onEventListener(CancelOrderEvent event) {
        String orderNo = event.getOrderNo();
        System.out.println("orderNo = " + orderNo);
        // save event log
    }
}