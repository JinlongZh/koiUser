package com.koi.chat.mapper.mysql;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.chat.domain.entity.RoomDO;

import java.time.LocalDateTime;

/**
 * 房间映射器
 *
 * @author zjl
 * @date 2023/10/10
 */
public interface RoomMapper extends BaseMapper<RoomDO> {

    default void refreshActiveTime(Long roomId, Long chatMessageId, LocalDateTime createTime) {
        update(null, new LambdaUpdateWrapper<RoomDO>()
                .eq(RoomDO::getId, roomId)
                .set(RoomDO::getLastMsgId, chatMessageId)
                .set(RoomDO::getActiveTime, createTime));
    }
}




