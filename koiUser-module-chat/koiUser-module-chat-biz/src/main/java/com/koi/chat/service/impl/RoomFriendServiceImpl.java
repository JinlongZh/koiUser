package com.koi.chat.service.impl;

import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.domain.entity.RoomFriendDO;
import com.koi.chat.enums.RoomTypeEnum;
import com.koi.chat.mapper.mysql.RoomFriendMapper;
import com.koi.chat.service.RoomFriendService;
import com.koi.chat.service.RoomService;
import com.koi.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.koi.chat.convert.RoomConvert.generateRoomKey;
import static com.koi.chat.convert.RoomFriendConvert.buildFriendRoom;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;


/**
 * 单聊房间服务 impl
 *
 * @Author zjl
 * @Date 2023/10/13 17:36
 */
@Service
public class RoomFriendServiceImpl implements RoomFriendService {

    @Resource
    private RoomFriendMapper roomFriendMapper;
    @Resource
    private RoomService roomService;

    @Override
    public RoomFriendDO getByRoomId(Long roomId) {
        return roomFriendMapper.getByRoomId(roomId);
    }

    @Override
    public List<RoomFriendDO> getByRoomIdList(List<Long> roomIdList) {
        return roomFriendMapper.getByRoomIdList(roomIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomFriendDO getFriendRoom(Long userIdOne, Long userIdTwo) {
        String roomKey = generateRoomKey(Arrays.asList(userIdOne, userIdTwo));
        RoomFriendDO roomFriendDO = roomFriendMapper.selectByRoomKey(roomKey);
        // 如果没有就创建
        if (Objects.isNull(roomFriendDO)) {
            RoomDO roomDO = roomService.createRoom(RoomTypeEnum.FRIEND);
            roomFriendDO = createFriendRoom(roomDO.getId(), Arrays.asList(userIdOne, userIdTwo));
        }
        return roomFriendDO;
    }

    @Override
    public RoomFriendDO createFriendRoom(Long roomId, List<Long> userIdList) {
        if (userIdList.size() != 2) {
            throw new ServiceException(BAD_REQUEST.getCode(), "房间创建失败，好友数量不对");
        }
        RoomFriendDO roomFriendDO = buildFriendRoom(roomId, userIdList);
        roomFriendMapper.insert(roomFriendDO);
        return roomFriendDO;
    }

}
