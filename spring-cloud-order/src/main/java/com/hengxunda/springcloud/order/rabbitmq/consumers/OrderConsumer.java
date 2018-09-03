package com.hengxunda.springcloud.order.rabbitmq.consumers;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.rabbitmq.OrderConfig;
import com.hengxunda.springcloud.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = OrderConfig.ORDER_PAY_QUEUE_NAME, containerFactory = "containerFactory")
public class OrderConsumer {

    //bindings = {@QueueBinding(value = @Queue(value = OrderConfig.ORDER_PAY_QUEUE_NAME), exchange = @Exchange(value = OrderConfig.ORDER_PAY_EXCHANGE_NAME))}

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(@Payload Order order) {

        log.info("order = {}", order);

        log.info("==============执行订单支付开始==============");

        orderService.orderPay(order.getNumber(), order.getTotalAmount());

        log.info("==============执行订单支付完成==============");
    }

}
