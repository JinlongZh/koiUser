package com.koi.chat.mapper.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.chat.domain.entity.RoomFriendDO;

import java.util.List;

/**
 * 单聊房间映射器
 *
 * @author zjl
 * @date 2023/10/10
 */
public interface RoomFriendMapper extends BaseMapper<RoomFriendDO> {

    default RoomFriendDO getByRoomId(Long roomId) {
        return selectOne(new LambdaQueryWrapper<RoomFriendDO>()
                .eq(RoomFriendDO::getRoomId, roomId));
    }

    default RoomFriendDO selectByRoomKey(String roomKey) {
        return selectOne(new LambdaQueryWrapper<RoomFriendDO>()
                .eq(RoomFriendDO::getRoomKey, roomKey));
    }

    default List<RoomFriendDO> getByRoomIdList(List<Long> roomIdList) {
        return selectList(new LambdaQueryWrapper<RoomFriendDO>()
                .in(RoomFriendDO::getRoomId, roomIdList));
    }
}




