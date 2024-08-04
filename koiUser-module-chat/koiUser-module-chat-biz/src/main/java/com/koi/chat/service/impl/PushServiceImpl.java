package com.koi.chat.service.impl;

import com.koi.chat.domain.dto.WSBaseRespDTO;
import com.koi.chat.domain.dto.WebSocketPushMessageDTO;
import com.koi.chat.service.PushService;
import com.koi.framework.rabbitmq.core.producer.RabbitMqProducer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.WS_PUSH_EXCHANGE;
import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.WS_PUSH_ROUTING_KEY;


/**
 * websocket推送服务实现类
 *
 * @Author zjl
 * @Date 2023/10/13 18:26
 */
@Service
public class PushServiceImpl implements PushService {

    @Resource
    private RabbitMqProducer rabbitMqProducer;


    @Override
    public void sendPushMsg(WSBaseRespDTO<?> wsBaseRespDTO, List<Long> userIdList) {
        rabbitMqProducer.sendMessage(WS_PUSH_EXCHANGE, WS_PUSH_ROUTING_KEY, new WebSocketPushMessageDTO(userIdList, wsBaseRespDTO));
    }

    @Override
    public void sendPushMsg(WSBaseRespDTO<?> wsBaseRespDTO, Long userId) {
        rabbitMqProducer.sendMessage(WS_PUSH_EXCHANGE, WS_PUSH_ROUTING_KEY, new WebSocketPushMessageDTO(userId, wsBaseRespDTO));
    }

    @Override
    public void sendPushMsg(WSBaseRespDTO<?> wsBaseRespDTO) {
        rabbitMqProducer.sendMessage(WS_PUSH_EXCHANGE, WS_PUSH_ROUTING_KEY, new WebSocketPushMessageDTO(wsBaseRespDTO));
    }
}
