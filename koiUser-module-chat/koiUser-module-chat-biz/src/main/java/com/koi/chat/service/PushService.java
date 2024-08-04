package com.koi.chat.service;

import com.koi.chat.domain.dto.WSBaseRespDTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * websocket推送服务推送服务
 *
 * @Author zjl
 * @Date 2023/10/13 18:19
 */
public interface PushService {

    @Resource
    void sendPushMsg(WSBaseRespDTO<?> msg, List<Long> uidList);

    void sendPushMsg(WSBaseRespDTO<?> msg, Long uid);

    void sendPushMsg(WSBaseRespDTO<?> msg);

}
