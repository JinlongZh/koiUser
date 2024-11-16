package com.koi.chat.strategy.msg;

import cn.hutool.core.collection.CollectionUtil;
import com.koi.chat.domain.dto.MessageExtraDTO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.TextMessageReqVO;
import com.koi.chat.domain.vo.response.TextMessageRespVO;
import com.koi.chat.enums.MessageTypeEnum;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.chat.strategy.msg.factory.MessageHandlerFactory;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.json.JsonUtils;
import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;
import com.koi.system.api.user.UserInfoApi;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;


/**
 * 普通文本消息
 *
 * @Author zjl
 * @Date 2023/10/9 15:51
 */
@Component
public class TextMessageHandler extends AbstractMessageHandler<TextMessageReqVO> {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserInfoApi userInfoApi;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.TEXT;
    }

    @Override
    protected void checkMsg(TextMessageReqVO body, Long roomId, Long uid) {
        // 校验回复消息
        if (Objects.nonNull(body.getReplyMessageId())) {
            MessageDO messageDO = messageMapper.selectById(body.getReplyMessageId());
            if (Objects.isNull(messageDO)) {
                throw new ServiceException(BAD_REQUEST.getCode(), "回复消息不存在");
            }
        }
    }

    @Override
    protected void saveMsg(MessageDO messageDO, TextMessageReqVO body) {
        MessageExtraDTO extra;
        if (messageDO.getExtra() == null || messageDO.getExtra().isEmpty()) {
            extra = new MessageExtraDTO();
        } else {
            extra = JsonUtils.parseObject(messageDO.getExtra(), MessageExtraDTO.class);
        }

        MessageDO update = new MessageDO();
        update.setContent(body.getContent());
        update.setId(messageDO.getId());
        update.setExtra(JsonUtils.toJsonString(extra));
        // 如果有回复消息
        if (Objects.nonNull(body.getReplyMessageId())) {
            update.setReplyMessageId(body.getReplyMessageId());
        }
        //艾特功能
        if (CollectionUtil.isNotEmpty(body.getAtUserIdList())) {
            extra.setAtUserIdList(body.getAtUserIdList());
        }

        messageMapper.updateById(update);
    }

    @Override
    public Object showMessage(MessageDO messageDO) {
        // 额外信息
        MessageExtraDTO extraDTO = JsonUtils.parseObject(messageDO.getExtra(), MessageExtraDTO.class);
        TextMessageRespVO textMessageRespVO = new TextMessageRespVO();

        textMessageRespVO.setContent(messageDO.getContent());
        // @用户id
        textMessageRespVO.setAtUserIdList(Optional.ofNullable(extraDTO)
                .map(MessageExtraDTO::getAtUserIdList)
                .orElse(null));
        // 查询回复消息
        Optional<MessageDO> reply = Optional.ofNullable(messageDO.getReplyMessageId())
                .map(messageMapper::selectById)
                .filter(a -> Objects.equals(a.getStatus(), CommonStatusEnum.ENABLE.getStatus()));

        if (reply.isPresent()) {
            MessageDO replyMessage = reply.get();
            TextMessageRespVO.ReplyMessage replyMsgVO = new TextMessageRespVO.ReplyMessage();
            replyMsgVO.setId(replyMessage.getId());
            replyMsgVO.setUserId(replyMessage.getFromUserId());
            replyMsgVO.setType(replyMessage.getType());
            replyMsgVO.setBody(MessageHandlerFactory.getStrategyNoNull(replyMessage.getType()).showReplyMessage(replyMessage));
            UserInfoRespDTO replyUser = userInfoApi.getUserInfoByUserId(replyMessage.getFromUserId());
            replyMsgVO.setNickname(replyUser.getNickname());

            textMessageRespVO.setReply(replyMsgVO);
        }
        return textMessageRespVO;

    }

    @Override
    public Object showReplyMessage(MessageDO messageDO) {
        return messageDO.getContent();
    }

    @Override
    public String showContactMessage(MessageDO messageDO) {
        return messageDO.getContent();
    }
}
