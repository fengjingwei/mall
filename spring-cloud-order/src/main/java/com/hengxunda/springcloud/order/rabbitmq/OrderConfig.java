package com.hengxunda.springcloud.order.rabbitmq;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OrderConfig {

    public static final String PAY_ORDER_QUEUE = "pay.order.queue";
    public static final String PAY_ORDER_EXCHANGE = "pay.order.exchange";
    public static final String PAY_ORDER_ROUTING_KEY = "pay.order.routing.key";

    public static final String PAY_ORDER_DELAY_QUEUE = "pay.order.delay.queue";
    public static final String PAY_ORDER_DEAD_LETTER_EXCHANGE = "pay.order.delay.letter.exchange";
    public static final String PAY_ORDER_DELAY_ROUTING_KEY = "pay.order.delay.routing.key";

    @Bean
    public Queue payOrderQueue() {
        return new Queue(PAY_ORDER_QUEUE, true);
    }

    @Bean
    public DirectExchange payOrderDirectExchange() {
        return new DirectExchange(PAY_ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Binding payOrderBinding(Queue payOrderQueue, DirectExchange payOrderDirectExchange) {
        return BindingBuilder.bind(payOrderQueue).to(payOrderDirectExchange).with(PAY_ORDER_ROUTING_KEY);
    }

    /**
     * 创建延时队列
     *
     * @return
     */
    @Bean
    public Queue payOrderDelayQueue() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("x-dead-letter-exchange", PAY_ORDER_QUEUE);
        params.put("x-dead-letter-routing-key", PAY_ORDER_ROUTING_KEY);
        return new Queue(PAY_ORDER_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange payOrderDeadLetterExchange() {
        return new DirectExchange(PAY_ORDER_DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding payOrderDelayBinding(Queue payOrderDelayQueue, DirectExchange payOrderDeadLetterExchange) {
        return BindingBuilder.bind(payOrderDelayQueue).to(payOrderDeadLetterExchange).with(PAY_ORDER_DELAY_ROUTING_KEY);
    }
}
