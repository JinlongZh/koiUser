package com.koi.chat.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.koi.chat.convert.MessageConvert;
import com.koi.chat.domain.dto.MsgSendMessageDTO;
import com.koi.chat.domain.entity.ContactDO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.domain.vo.request.MessageReqVO;
import com.koi.chat.domain.vo.request.MessagePageReqVO;
import com.koi.chat.domain.vo.request.ReadMessageReqVO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.chat.mapper.mysql.ContactMapper;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.chat.mapper.mysql.RoomMapper;
import com.koi.chat.service.ChatService;
import com.koi.chat.strategy.msg.AbstractMessageHandler;
import com.koi.chat.strategy.msg.factory.MessageHandlerFactory;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.framework.rabbitmq.core.producer.RabbitMqProducer;
import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;
import com.koi.system.api.user.UserInfoApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.SEND_MESSAGE_EXCHANGE;
import static com.koi.framework.rabbitmq.core.constant.RabbitMqConstant.SEND_MESSAGE_ROUTING_KEY;


/**
 * 聊天 service实现类
 *
 * @Author zjl
 * @Date 2023/10/8 22:30
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private RabbitMqProducer rabbitMqProducer;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private ContactMapper contactMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private UserInfoApi userInfoApi;


    @Override
    public Long sendMsg(MessageReqVO chatMessageReqVO, Long userId) {
        // 检查房间状态
        checkRoom(chatMessageReqVO, userId);
        // 策略模式处理不同的消息类型
        AbstractMessageHandler<?> msgHandler = MessageHandlerFactory.getStrategyNoNull(chatMessageReqVO.getMessageType());
        Long msgId = msgHandler.checkAndSaveMsg(chatMessageReqVO, userId);
        // 发布消息发送事件
        rabbitMqProducer.sendMessage(SEND_MESSAGE_EXCHANGE, SEND_MESSAGE_ROUTING_KEY, new MsgSendMessageDTO(msgId));
        return msgId;
    }

    @Override
    public MessageRespVO getMessageResp(MessageDO chatMessageDO, Long receiveUserId) {
        return CollUtil.getFirst(getMessageRespBatch(Collections.singletonList(chatMessageDO), receiveUserId));
    }

    @Override
    public MessageRespVO getMessageResp(Long messageId, Long receiveUserId) {
        MessageDO chatMessageDO = messageMapper.selectById(messageId);
        return getMessageResp(chatMessageDO, receiveUserId);
    }

    @Override
    public PageResult<MessageRespVO> getMessagePage(Long userId, MessagePageReqVO messagePageReqVO) {
        PageResult<MessageDO> messagePage = messageMapper.getMessagePage(messagePageReqVO, null);
        if (messagePage.getList().isEmpty()) {
            return new PageResult<>();
        }
        List<MessageRespVO> chatMessageRespVOList = getMessageRespBatch(messagePage.getList(), userId);
        return new PageResult<>(chatMessageRespVOList, messagePage.getTotal());
    }

    @Override
    public void readMessage(Long loginUserId, ReadMessageReqVO readMessageReqVO) {
        ContactDO contactDO = contactMapper.getByRoomId(readMessageReqVO.getRoomId(), loginUserId);
        if (Objects.nonNull(contactDO)) {
            // 更新已读时间
            ContactDO update = new ContactDO();
            update.setId(contactDO.getId());
            update.setReadTime(LocalDateTime.now());
            contactMapper.updateById(update);
        } else {
            // 插入已读时间
            ContactDO insert = new ContactDO();
            insert.setUserId(loginUserId);
            insert.setRoomId(readMessageReqVO.getRoomId());
            insert.setReadTime(LocalDateTime.now());
            contactMapper.insert(insert);
        }
    }


    /**
     * 批量获取消息
     *
     * @param chatMessageListDO
     * @param receiveUserId
     * @Return List<MessageRespVO>
     */
    private List<MessageRespVO> getMessageRespBatch(List<MessageDO> chatMessageListDO, Long receiveUserId) {
        if (CollectionUtil.isEmpty(chatMessageListDO)) {
            return new ArrayList<>();
        }
//        Map<Long, MessageDO> replyMap = new HashMap<>();
//        //批量查出回复的消息
//        List<Long> replyIds = chatMessageListDO.stream().map(MessageDO::getReplyMessageId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
//        if (CollectionUtil.isNotEmpty(replyIds)) {
//            replyMap = messageMapper.selectBatchIds(replyIds).stream().collect(Collectors.toMap(MessageDO::getId, Function.identity()));
//        }

        // 查询用户信息
        Set<Long> userIdSet = chatMessageListDO.stream().map(MessageDO::getFromUserId).collect(Collectors.toSet());
        Map<Long, UserInfoRespDTO> userInfoMap = userInfoApi.getUserInfoByUserIds(userIdSet);

        return MessageConvert.buildMsgResp(chatMessageListDO, receiveUserId, userInfoMap);
    }

    /**
     * 检查room状态
     *
     * @param chatMessageReqVO
     * @param userId
     * @Return void
     */
    private void checkRoom(MessageReqVO chatMessageReqVO, Long userId) {
        RoomDO roomDO = roomMapper.selectById(chatMessageReqVO.getRoomId());
        if (Objects.isNull(roomDO)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "房间不存在");
        }
    }
}
