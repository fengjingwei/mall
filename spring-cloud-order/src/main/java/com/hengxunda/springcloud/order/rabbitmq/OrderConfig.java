package com.hengxunda.springcloud.order.rabbitmq;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootConfiguration
public class OrderConfig {

    public static final String ORDER_PAY_QUEUE = "order.pay.queue";
    public static final String ORDER_PAY_EXCHANGE = "order.pay.exchange";
    public static final String ORDER_PAY_ROUTING_KEY = "order.pay.routing.key";

    public static final String ORDER_PAY_DELAY_QUEUE = "order.pay.delay.queue";
    public static final String ORDER_PAY_DEAD_LETTER_EXCHANGE = "order.pay.delay.letter.exchange";
    public static final String ORDER_PAY_DELAY_ROUTING_KEY = "order.pay.delay.routing.key";

    @Bean
    public Queue orderPayQueue() {
        return new Queue(ORDER_PAY_QUEUE, true);
    }

    @Bean
    public DirectExchange orderPayDirectExchange() {
        return new DirectExchange(ORDER_PAY_EXCHANGE, true, false);
    }

    @Bean
    public Binding orderPayBinding(Queue orderPayQueue, DirectExchange orderPayDirectExchange) {
        return BindingBuilder.bind(orderPayQueue).to(orderPayDirectExchange).with(ORDER_PAY_ROUTING_KEY);
    }

    /**
     * 创建延时队列
     *
     * @return
     */
    @Bean
    public Queue orderPayDelayQueue() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("x-dead-letter-exchange", ORDER_PAY_QUEUE);
        params.put("x-dead-letter-routing-key", ORDER_PAY_ROUTING_KEY);
        return new Queue(ORDER_PAY_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange orderPayDeadLetterExchange() {
        return new DirectExchange(ORDER_PAY_DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding orderPayDelayBinding(Queue orderPayDelayQueue, DirectExchange orderPayDeadLetterExchange) {
        return BindingBuilder.bind(orderPayDelayQueue).to(orderPayDeadLetterExchange).with(ORDER_PAY_DELAY_ROUTING_KEY);
    }
}
