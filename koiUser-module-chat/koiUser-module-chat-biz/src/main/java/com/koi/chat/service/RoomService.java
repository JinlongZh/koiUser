package com.koi.chat.service;

import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.enums.RoomTypeEnum;

/**
 * 房间 service
 *
 * @Author zjl
 * @Date 2023/10/17 17:14
 */
public interface RoomService {

    RoomDO createRoom(RoomTypeEnum typeEnum);

}
