package com.koi.chat.strategy.msg;

import com.koi.chat.domain.dto.MessageExtraDTO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.TextMessageReqVO;
import com.koi.chat.enums.MessageTypeEnum;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.json.JsonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

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

//    @Autowired
//    private SensitiveWordBs sensitiveWordBs;

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

        messageMapper.updateById(update);
    }

    @Override
    public Object showMessage(MessageDO messageDO) {
        return messageDO.getContent();
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
