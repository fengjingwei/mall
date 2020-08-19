package com.hengxunda.springcloud.common.event.publisher;

import java.util.Collection;

public interface DomainEventPublisher {

    void post(Object event);

    void post(Collection<?> events);

    void register(Object object);

    void unregister(Object object);
}
