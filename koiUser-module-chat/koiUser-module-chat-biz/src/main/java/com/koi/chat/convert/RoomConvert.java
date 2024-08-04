package com.koi.chat.convert;

import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.domain.entity.RoomFriendDO;
import com.koi.chat.enums.RoomTypeEnum;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/10/16 21:45
 */
public interface RoomConvert {

    String SEPARATOR = ",";

    static Long getFriendUserId(RoomFriendDO roomFriendDO, Long userId) {
        return Objects.equals(userId, roomFriendDO.getUserIdOne()) ? roomFriendDO.getUserIdTwo() : roomFriendDO.getUserIdOne();
    }

    static Set<Long> getFriendUserIdSet(Collection<RoomFriendDO> values, Long uid) {
        return values.stream()
                .map(a -> getFriendUserId(a, uid))
                .collect(Collectors.toSet());
    }

    /**
     * 生成房间key
     *
     * @param UserIdList
     * @return
     */
    static String generateRoomKey(List<Long> UserIdList) {
        return UserIdList.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    static RoomDO buildRoom(RoomTypeEnum typeEnum) {
        RoomDO roomDO = new RoomDO();
        roomDO.setType(typeEnum.getType());
        return roomDO;
    }

}
