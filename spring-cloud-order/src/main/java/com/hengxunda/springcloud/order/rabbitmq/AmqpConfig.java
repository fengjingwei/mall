package com.hengxunda.springcloud.order.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    private static final int DEFAULT_CONCURRENT = 1, DEFAULT_MAX_CONCURRENT = 5, DEFAULT_PREFETCH_COUNT = 10;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setPrefetchCount(DEFAULT_PREFETCH_COUNT);
        containerFactory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        containerFactory.setMaxConcurrentConsumers(DEFAULT_MAX_CONCURRENT);
        containerFactory.setDefaultRequeueRejected(false);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        configurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }
}
