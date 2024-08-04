package com.koi.framework.rabbitmq.core.constant;

/**
 * rabbitmq配置命名
 *
 * @author xielinkun
 * @date 2023/8/24
 */
public class RabbitMqConstant {

    //******************* 消息发送 *******************//

    /**
     * 消息发送交换机
     */
    public final static String SEND_MESSAGE_EXCHANGE = "chat.send.message.exchange";

    /**
     * 消息发送队列
     */
    public final static String SEND_MESSAGE_QUEUE = "chat.send.message.queue";

    /**
     * 消息发送路由
     */
    public final static String SEND_MESSAGE_ROUTING_KEY = "chat.send.message.routingKey";


    //******************* 消息发送 *******************//

    /**
     * ws推送交换机
     */
    public final static String WS_PUSH_EXCHANGE = "websocket.push.exchange";

    /**
     * ws推送队列
     */
    public final static String WS_PUSH_QUEUE = "websocket.push.queue";

    /**
     * ws推送路由
     */
    public final static String WS_PUSH_ROUTING_KEY = "websocket.push.routingKey";

}
