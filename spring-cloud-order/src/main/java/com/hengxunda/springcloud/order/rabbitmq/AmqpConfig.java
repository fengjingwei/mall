package com.hengxunda.springcloud.order.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AmqpConfig {

    /**
     * 如果有多个消费者同时订阅同一个Queue中的消息,Queue中的消息会被平摊给多个消费者
     * 如果每个消息的处理时间不同,就有可能会导致某些消费者一直在忙,而另外一些消费者很
     * 快就处理完手头工作并一直空闲的情况
     *
     */

    /**
     * 消费者数量,默认10
     */
    public static final int DEFAULT_CONCURRENT = 10;

    /**
     * 设置prefetchCount来限制Queue每次发送给每个消费者的消息数,
     * 默认Queue每次给每个消费者发送100条消息,消费者处理完这条消息后,
     * Queue会再给该消费者发送100条消息
     * <p>
     * 每个消费者获取最大投递数量,默认100
     */
    public static final int DEFAULT_PREFETCH_COUNT = 100;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean("containerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(DEFAULT_PREFETCH_COUNT);
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        connectionFactory.createConnection().createChannel(true);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
