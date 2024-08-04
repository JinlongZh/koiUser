package com.koi.chat.strategy.msg;

import com.koi.chat.domain.dto.MessageExtraDTO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.ImageMessageReqVO;
import com.koi.chat.enums.MessageTypeEnum;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.common.utils.json.JsonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 图片消息
 *
 * @Author zjl
 * @Date 2023/12/10 16:33
 */
@Component
public class ImageMessageHandle extends AbstractMessageHandler<ImageMessageReqVO>{

    @Resource
    private MessageMapper messageMapper;

    @Override
    MessageTypeEnum getMsgTypeEnum() {
        return MessageTypeEnum.IMG;
    }

    @Override
    protected void saveMsg(MessageDO messageDO, ImageMessageReqVO body) {
        MessageExtraDTO extra = Optional.ofNullable(JsonUtils.parseObject(messageDO.getExtra(), MessageExtraDTO.class)).orElse(new MessageExtraDTO());
        extra.setImageMessageReqVO(body);

        MessageDO update = new MessageDO();
        update.setId(messageDO.getId());
        update.setExtra(JsonUtils.toJsonString(extra));

        messageMapper.updateById(update);
    }

    @Override
    public Object showMessage(MessageDO messageDO) {
        MessageExtraDTO messageExtraDTO = JsonUtils.parseObject(messageDO.getExtra(), MessageExtraDTO.class);
        return messageExtraDTO.getImageMessageReqVO();
    }

    @Override
    public Object showReplyMessage(MessageDO messageDO) {
        return "图片";
    }

    @Override
    public String showContactMessage(MessageDO messageDO) {
        return "[图片]";
    }
}
