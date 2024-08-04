package com.koi.framework.rabbitmq.core.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import javax.annotation.Resource;

/**
 * rabbitmq 生产者
 *
 * @Author zjl
 * @Date 2023/10/10 17:39
 */
@AutoConfiguration
public class RabbitMqProducer {

    private static final Integer MINUTE = 1000;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由key
     * @param body 参数体
     */
    public void sendMessage(String exchange, String routingKey, Object body) {
        rabbitTemplate.convertAndSend(exchange, routingKey, body);
    }

    /**
     * 发送延迟消息
     * @param exchange 交换机
     * @param routingKey 路由key
     * @param body 参数体
     * @param delayTime 延迟时间
     */
    public void sendDelayMessage(String exchange, String routingKey, Object body, Integer delayTime){
        rabbitTemplate.convertAndSend(exchange,routingKey,body, message -> {
            message.getMessageProperties().setDelay(delayTime * MINUTE);
            return message;
        });
    }
}
