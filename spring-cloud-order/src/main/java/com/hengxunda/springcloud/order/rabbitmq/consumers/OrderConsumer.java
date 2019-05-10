package com.hengxunda.springcloud.order.rabbitmq.consumers;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.rabbitmq.OrderConfig;
import com.hengxunda.springcloud.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = OrderConfig.ORDER_PAY_QUEUE, containerFactory = "rabbitListenerContainerFactory")
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(@Payload Order order, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("order = {}", order);
        try {
            orderService.orderPay(order.getNumber(), order.getTotalAmount());
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
