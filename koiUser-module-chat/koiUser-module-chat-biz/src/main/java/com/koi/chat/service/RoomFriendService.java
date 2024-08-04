package com.koi.chat.service;

import com.koi.chat.domain.entity.RoomFriendDO;

import java.util.List;

/**
 * 单聊房间服务
 *
 * @author zjl
 * @date 2023/10/13
 */
public interface RoomFriendService {

    /**
     * 按房间id获取单聊房间
     *
     * @param roomId roomId
     * @return 单聊房间表
     */
    RoomFriendDO getByRoomId(Long roomId);

    /**
     * 按房间id列表获取
     *
     * @param roomIdList 房间id列表
     * @return
     */
    List<RoomFriendDO> getByRoomIdList(List<Long> roomIdList);

    /**
     * 获取单聊房间
     *
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    RoomFriendDO getFriendRoom(Long userIdOne, Long userIdTwo);

    /**
     * 创建好友聊天室
     * 创建单聊房间
     *
     * @param userIdList 用户id列表
     * @param roomId     房间id
     * @return 单聊房间表
     */
    RoomFriendDO createFriendRoom(Long roomId, List<Long> userIdList);
}
