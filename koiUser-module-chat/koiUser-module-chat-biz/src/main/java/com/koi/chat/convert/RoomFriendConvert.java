package com.koi.chat.convert;

import com.koi.chat.domain.entity.RoomFriendDO;
import com.koi.common.enums.CommonStatusEnum;

import java.util.List;
import java.util.stream.Collectors;

import static com.koi.chat.convert.RoomConvert.generateRoomKey;

/**
 * 单聊房间convert
 *
 * @Author zjl
 * @Date 2023/10/17 17:35
 */
public interface RoomFriendConvert {

    static RoomFriendDO buildFriendRoom(Long roomId, List<Long> userIdList) {
        List<Long> collect = userIdList.stream().sorted().collect(Collectors.toList());
        RoomFriendDO roomFriendDO = new RoomFriendDO();
        roomFriendDO.setRoomId(roomId);
        roomFriendDO.setUserIdOne(collect.get(0));
        roomFriendDO.setUserIdTwo(collect.get(1));
        roomFriendDO.setRoomKey(generateRoomKey(userIdList));
        roomFriendDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        return roomFriendDO;
    }

}
