package com.koi.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.*;

/**
 * 交换机配置
 *
 * @author xielinkun
 * @date 2023/8/24
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue sendMessageQueue() {
        // 声明队列
        return new Queue(SEND_MESSAGE_QUEUE);
    }

    @Bean
    public DirectExchange sendMessageExchange() {
        // 声明直连交换机
        return new DirectExchange(SEND_MESSAGE_EXCHANGE);
    }

    @Bean
    public Binding sendMessageBinding(@Qualifier("sendMessageQueue") Queue queue,
                                  @Qualifier("sendMessageExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SEND_MESSAGE_ROUTING_KEY);
    }


    @Bean
    public Queue wsPushQueue() {
        // 声明队列
        return new Queue(WS_PUSH_QUEUE);
    }

    @Bean
    public DirectExchange wsPushExchange() {
        // 声明直连交换机
        return new DirectExchange(WS_PUSH_EXCHANGE);
    }

    @Bean
    public Binding wsPushBinding(@Qualifier("wsPushQueue") Queue queue,
                                 @Qualifier("wsPushExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(WS_PUSH_ROUTING_KEY);
    }


}
