package com.hengxunda.springcloud.common.event.publisher;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "guavaDomainEventPublisher")
public class GuavaDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private EventBus eventBus;

    @Override
    public void publish(Object event) {
        eventBus.post(event);
    }

    @Override
    public void register(Object object) {
        eventBus.register(object);
    }
}
