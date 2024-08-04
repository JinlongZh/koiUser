package com.koi.chat.mapper.mysql;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.MessagePageReqVO;
import com.koi.common.domain.PageResult;
import com.koi.framework.mybatis.utils.MyBatisUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.koi.common.enums.CommonStatusEnum.ENABLE;


/**
 * 聊天消息映射器
 *
 * @author zjl
 * @date 2023/10/09
 */
public interface MessageMapper extends BaseMapper<MessageDO> {

    default Integer getUnReadCount(Long roomId, LocalDateTime readTime, Long formUserId) {
        return selectCount(new LambdaQueryWrapper<MessageDO>()
                .eq(MessageDO::getRoomId, roomId)
                .ne(MessageDO::getFromUserId, formUserId)
                .gt(Objects.nonNull(readTime), MessageDO::getCreateTime, readTime)).intValue();
    }

    default PageResult<MessageDO> getMessagePage(MessagePageReqVO messagePageReqVO, Long lastMessageId) {
        Page<MessageDO> page = MyBatisUtils.buildPage(messagePageReqVO);
        Page<MessageDO> result = selectPage(page, new LambdaQueryWrapper<MessageDO>()
                .eq(MessageDO::getRoomId, messagePageReqVO.getRoomId())
                .eq(MessageDO::getStatus, ENABLE.getStatus())
                .eq(Objects.nonNull(lastMessageId), MessageDO::getId, lastMessageId)
                .orderByDesc(MessageDO::getCreateTime));
        return new PageResult<>(result.getRecords(), result.getTotal());
    }
}




