package com.hengxunda.springcloud.common.event.publisher;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component(value = "guavaDomainEventPublisher")
public class GuavaDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private EventBus eventBus;

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }

    @Override
    public void post(Collection<?> events) {
        events.forEach(eventBus::post);
    }

    @Override
    public void register(Object object) {
        eventBus.register(object);
    }

    @Override
    public void unregister(Object object) {
        eventBus.unregister(object);
    }
}
