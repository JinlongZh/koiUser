package com.koi.chat.convert;

import cn.hutool.core.bean.BeanUtil;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.MessageReqVO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.chat.strategy.msg.AbstractMessageHandler;
import com.koi.chat.strategy.msg.factory.MessageHandlerFactory;
import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.koi.common.enums.CommonStatusEnum.ENABLE;

/**
 * 消息转换
 *
 * @author zjl
 * @date 2023/10/10
 */
public interface MessageConvert {

    static MessageDO convertMessage(MessageReqVO chatMessageReqVO, Long userId) {
        return MessageDO.builder()
                .fromUserId(userId)
                .roomId(chatMessageReqVO.getRoomId())
                .type(chatMessageReqVO.getMessageType())
                .status(ENABLE.getStatus())
                .build();
    }

    static List<MessageRespVO> buildMsgResp(List<MessageDO> chatMessageListDO, Long receiveUserId, Map<Long, UserInfoRespDTO> userInfoMap) {
        return chatMessageListDO.stream().map(a -> {
                    MessageRespVO resp = new MessageRespVO();
                    resp.setFromUser(buildFromUser(a.getFromUserId(), userInfoMap));
                    resp.setMessage(buildMessage(a, receiveUserId));
                    return resp;
                })
                // 根据前端排好序，更方便它展示
                .sorted(Comparator.comparing(a -> a.getMessage().getCreateTime()))
                .collect(Collectors.toList());
    }

    static MessageRespVO.UserInfo buildFromUser(Long fromUserId, Map<Long, UserInfoRespDTO> userInfoMap) {
        MessageRespVO.UserInfo userInfo = new MessageRespVO.UserInfo();
        UserInfoRespDTO userDTO = userInfoMap.get(fromUserId);

        userInfo.setUserId(fromUserId);
        userInfo.setNickname(userDTO.getNickname());
        userInfo.setAvatar(userDTO.getAvatar());
        userInfo.setIpAddress(userDTO.getLoginIp());

        return userInfo;
    }

    static MessageRespVO.Message buildMessage(MessageDO messageDO, Long receiveUserId) {
        MessageRespVO.Message messageVO = new MessageRespVO.Message();
        BeanUtil.copyProperties(messageDO, messageVO);
        AbstractMessageHandler<?> msgHandler = MessageHandlerFactory.getStrategyNoNull(messageDO.getType());
        if (Objects.nonNull(msgHandler)) {
            messageVO.setBody(msgHandler.showMessage(messageDO));
        }
        return messageVO;
    }
}
