package com.koi.framework.rabbitmq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;

/**
 * rabbit mq自动配置
 *
 * @author zjl
 * @date 2023/10/10
 */
@AutoConfiguration
public class RabbitMqAutoConfiguration {

    /**
     * 发送端序列化
     *
     * @param connectionFactory
     * @param rabbitTemplateConfigurer
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitTemplateConfigurer rabbitTemplateConfigurer) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(converter);
        rabbitTemplateConfigurer.configure(rabbitTemplate, connectionFactory);
        return rabbitTemplate;
    }

    /**
     * 接收端反序列化
     *
     * @param factoryConfigurer
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factoryConfigurer.configure(factory, connectionFactory);
        return factory;
    }

}
