package com.hengxunda.springcloud.order.rabbitmq.producers;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.rabbitmq.OrderConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void payment(Order order) {
        amqpTemplate.convertAndSend(OrderConfig.PAY_ORDER_EXCHANGE, OrderConfig.PAY_ORDER_ROUTING_KEY, order);
    }
}
