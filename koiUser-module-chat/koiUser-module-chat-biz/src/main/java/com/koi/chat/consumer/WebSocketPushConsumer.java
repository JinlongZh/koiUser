package com.koi.chat.consumer;

import com.koi.chat.domain.dto.WebSocketPushMessageDTO;
import com.koi.chat.enums.WebSocketPushTypeEnum;
import com.koi.chat.service.WebSocketService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.WS_PUSH_QUEUE;


/**
 * websocket推送消费者
 *
 * @Author zjl
 * @Date 2023/10/9 18:20
 */
@Slf4j
@Component
public class WebSocketPushConsumer {

    @Resource
    private WebSocketService webSocketService;

    @RabbitListener(queues = WS_PUSH_QUEUE, ackMode = "MANUAL")
    public void receiveMessage(WebSocketPushMessageDTO pushMessageDTO, Channel channel, Message m) throws IOException {
        try {
            WebSocketPushTypeEnum webSocketPushTypeEnum = WebSocketPushTypeEnum.of(pushMessageDTO.getPushType());
            switch (webSocketPushTypeEnum) {
                case USER:
                    pushMessageDTO.getUserIdList().forEach(userId -> {
                        webSocketService.sendToUserId(pushMessageDTO.getWsBaseMsg(), userId);
                    });
                    break;
                case ALL:
                    webSocketService.sendToAllOnline(pushMessageDTO.getWsBaseMsg(), null);
                    break;
            }
            // 手动ACK
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("websocket推送失败: {}", e.getMessage(), e);
            // 重试或其他处理方式，例如放回队列、记录日志等
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
