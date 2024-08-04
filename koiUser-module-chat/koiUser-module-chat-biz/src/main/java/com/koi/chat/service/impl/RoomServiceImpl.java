package com.koi.chat.service.impl;

import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.enums.RoomTypeEnum;
import com.koi.chat.mapper.mysql.RoomMapper;
import com.koi.chat.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.koi.chat.convert.RoomConvert.buildRoom;


/**
 * 房间 service实现类
 *
 * @Author zjl
 * @Date 2023/10/17 17:18
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;


    @Override
    public RoomDO createRoom(RoomTypeEnum typeEnum) {
        RoomDO insert = buildRoom(typeEnum);
        roomMapper.insert(insert);
        return insert;
    }
}
