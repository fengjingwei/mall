package com.hengxunda.springcloud.common.event.publisher;

public interface DomainEventPublisher {

    void publish(Object event);

    void register(Object object);
}
