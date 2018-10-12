package com.hengxunda.springcloud.order.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class OrderConfig {

    public static final String ORDER_PAY_QUEUE_NAME = "order.pay.queue";
    public static final String ORDER_PAY_ROUTING_KEY = "order.pay.routingKey";
    public static final String ORDER_PAY_EXCHANGE_NAME = "order.pay.exchange";

    @Bean
    public Queue orderPayQueue() {
        // 队列持久化
        return new Queue(ORDER_PAY_QUEUE_NAME, true);
    }

    /**
     * 如果生产者的routing key和Binding中的routing key一致,此交换机就将消息发到对应的队列中,完全匹配、单播的模式
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(ORDER_PAY_EXCHANGE_NAME, true, false);
    }

    /**
     * 将routing key和某个模式进行匹配,需要队列binding到一个模式上
     *
     * @return
     */
    /*@Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(ORDER_PAY_EXCHANGE_NAME, true, false);
    }*/

    /**
     * 广播模式:
     * Fanout Exchange不需要处理RouteKey,只需要简单的将队列绑定到exchange上,
     * 这样发送到exchange的消息都会被转发到与该交换机绑定的所有队列上,
     * 所以,Fanout Exchange转发消息是最快的
     *
     * @return
     */
    /*@Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(ORDER_PAY_EXCHANGE_NAME, true, false);
    }*/
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(orderPayQueue()).to(directExchange()).with(ORDER_PAY_ROUTING_KEY);
    }
}
