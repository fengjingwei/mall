package com.hengxunda.springcloud.order.rabbitmq;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Log4j2
@Configuration
public class RabbitTemplateConfig implements ConfirmCallback, ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 异步监听消息是否到达Exchange
     *
     * @param correlationData 消息唯一标识
     * @param ack             是否确认，true表示ack，false表示nack
     * @param cause           nack失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("correlationData = {}, ack = {}, cause = {}", correlationData, false, cause);
        }
    }

    /**
     * 异步监听消息是否到达Queue
     * 触发回调的前提：
     * 1.消息已经到达Exchange
     * 2.消息无法到达Queue，比如Exchange找不到和RoutingKey绑定的Queue
     *
     * @param message    消息主体
     * @param replyCode  失败状态码
     * @param replyText  失败原因
     * @param exchange   交换机
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("message = {}, replyCode = {}, replyText = {}, exchange = {}, routingKey = {}", message, replyCode, replyText, exchange, routingKey);
    }
}
