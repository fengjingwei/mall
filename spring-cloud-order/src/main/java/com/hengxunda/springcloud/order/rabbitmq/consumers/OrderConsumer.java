package com.hengxunda.springcloud.order.rabbitmq.consumers;

import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.rabbitmq.OrderConfig;
import com.hengxunda.springcloud.order.service.PaymentService;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@RabbitListener(queues = OrderConfig.PAY_ORDER_QUEUE, containerFactory = "rabbitListenerContainerFactory")
public class OrderConsumer {

    @Autowired
    private PaymentService paymentService;

    @RabbitHandler
    public void process(@Payload Order order, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        log.info("order = {}", order);
        try {
            paymentService.makePayment(order);
        } catch (Exception e) {
            e.printStackTrace();
            // 进行失败业务处理
        } finally {
            channel.basicAck(deliveryTag, false);
        }
    }
}
