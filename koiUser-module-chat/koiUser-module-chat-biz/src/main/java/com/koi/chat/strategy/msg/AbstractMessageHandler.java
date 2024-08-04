package com.koi.chat.strategy.msg;

import cn.hutool.core.bean.BeanUtil;
import com.koi.chat.convert.MessageConvert;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.MessageReqVO;
import com.koi.chat.enums.MessageTypeEnum;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.chat.strategy.msg.factory.MessageHandlerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

/**
 * 消息处理器抽象类
 *
 * @Author zjl
 * @Date 2023/10/8 23:10
 */
public abstract class AbstractMessageHandler<Req> {

    @Resource
    private MessageMapper messageMapper;

    private Class<Req> bodyClass;

    @PostConstruct
    private void init() {
        // 获取当前类的泛型超类（父类）的类型。
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.bodyClass = (Class<Req>) genericSuperclass.getActualTypeArguments()[0];
        MessageHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }
    /**
     * 消息类型
     */
    abstract MessageTypeEnum getMsgTypeEnum();

    protected void checkMsg(Req body, Long roomId, Long uid) {

    }

    @Transactional
    public Long checkAndSaveMsg(MessageReqVO messageReqVO, Long userId) {
        Req body = this.toBean(messageReqVO.getBody());
        //子类扩展校验
        checkMsg(body, messageReqVO.getRoomId(), userId);
        MessageDO messageDO = MessageConvert.convertMessage(messageReqVO, userId);
        // 统一保存
        messageMapper.insert(messageDO);
        //子类扩展保存
        saveMsg(messageDO, body);
        return messageDO.getId();
    }

    private Req toBean(Object body) {
        if (bodyClass.isAssignableFrom(body.getClass())) {
            return (Req) body;
        }
        return BeanUtil.toBean(body, bodyClass);
    }

    protected abstract void saveMsg(MessageDO messageDO, Req body);

    /**
     * 展示消息
     */
    public abstract Object showMessage(MessageDO messageDO);

    /**
     * 被回复时——展示的消息
     */
    public abstract Object showReplyMessage(MessageDO messageDO);

    /**
     * 会话列表——展示的消息
     */
    public abstract String showContactMessage(MessageDO messageDO);


}
