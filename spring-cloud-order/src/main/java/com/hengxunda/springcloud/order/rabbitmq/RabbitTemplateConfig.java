package com.hengxunda.springcloud.order.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootConfiguration
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
     * 消息发送确认后回调
     *
     * @param correlationData 消息唯一标识
     * @param ack             是否确认
     * @param cause           失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("correlationData = {}, ack = {}, cause = {}", correlationData, false, cause);
        }
    }

    /**
     * 消息发送失败后回调
     *
     * @param message    消息主体
     * @param replyCode  失败状态码
     * @param replyText  失败原因
     * @param exchange   交换器
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("message = {}, replyCode = {}, replyText = {}, exchange = {}, routingKey = {}", message, replyCode, replyText, exchange, routingKey);
    }
}
