package com.koi.chat.consumer;

import com.koi.chat.convert.WebSocketConvert;
import com.koi.chat.domain.dto.MsgSendMessageDTO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.domain.entity.RoomFriendDO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.chat.enums.RoomTypeEnum;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.chat.mapper.mysql.RoomMapper;
import com.koi.chat.service.ChatService;
import com.koi.chat.service.ContactService;
import com.koi.chat.service.PushService;
import com.koi.chat.service.RoomFriendService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.SEND_MESSAGE_QUEUE;


/**
 * 消息发送消费者
 *
 * @author zjl
 * @date 2023/10/10
 */
@Component
@Slf4j
public class MessageSendConsumer {

    @Resource
    private MessageMapper messageMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private ChatService chatService;
    @Resource
    private RoomFriendService roomFriendService;
    @Resource
    private ContactService contactService;
    @Resource
    private PushService pushService;


    @RabbitListener(queues = SEND_MESSAGE_QUEUE, ackMode = "MANUAL")
    public void receiveMessage(MsgSendMessageDTO msgSendMessageDTO, Channel channel, Message m) throws IOException {
        try {
            MessageDO messageDO = messageMapper.selectById(msgSendMessageDTO.getMsgId());
            RoomDO roomDO = roomMapper.selectById(messageDO.getRoomId());
            MessageRespVO messageRespVO = chatService.getMessageResp(messageDO, null);
            roomMapper.refreshActiveTime(roomDO.getId(), messageDO.getId(), messageDO.getCreateTime());

            List<Long> memberUidList = new ArrayList<>();
            if (Objects.equals(roomDO.getType(), RoomTypeEnum.FRIEND.getType())) {
                // 单聊逻辑处理
                RoomFriendDO roomFriendDO = roomFriendService.getByRoomId(roomDO.getId());
                memberUidList = Arrays.asList(roomFriendDO.getUserIdOne(), roomFriendDO.getUserIdTwo());

            } else if (Objects.equals(roomDO.getType(), RoomTypeEnum.GROUP.getType())) {
                // 群聊逻辑处理

            }
            // 更新所有房间成员的会话时间
            contactService.refreshOrCreateActiveTime(roomDO.getId(), memberUidList, messageDO.getId(), messageDO.getCreateTime());
            // 推送房间成员
            pushService.sendPushMsg(WebSocketConvert.buildMessageSend(messageRespVO), memberUidList);

            // 手动ACK
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消息发送处理失败: {}", e.getMessage(), e);
            // 重试或其他处理方式，例如放回队列、记录日志等
            channel.basicAck(m.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
