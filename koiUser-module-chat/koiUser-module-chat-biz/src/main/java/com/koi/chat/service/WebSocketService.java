package com.koi.chat.service;

import com.koi.chat.domain.dto.WSAuthorize;
import com.koi.chat.domain.dto.WSBaseRespDTO;
import io.netty.channel.Channel;

/**
 * websocket 服务
 *
 * @Author zjl
 * @Date 2024/8/3
 */
public interface WebSocketService {

    /**
     * 处理所有ws连接的事件
     *
     * @param channel
     */
    void connect(Channel channel);

    /**
     * 处理ws断开连接的事件
     *
     * @param channel
     */
    void removed(Channel channel);

    /**
     * 发送到uid
     *
     * @param wsBaseResp ws-base resp
     * @param uid        uid
     */
    void sendToUserId(WSBaseRespDTO<?> wsBaseResp, Long uid);

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseResp 发送的消息体
     * @param skipUid    需要跳过的人
     */
    void sendToAllOnline(WSBaseRespDTO<?> wsBaseResp, Long skipUid);

    /**
     * 推动消息给所有在线的人
     *
     * @param wsBaseResp 发送的消息体
     */
    void sendToAllOnline(WSBaseRespDTO<?> wsBaseResp);

    /**
     * 主动认证登录
     *
     * @param channel
     * @param wsAuthorize
     */
    void authorize(Channel channel, WSAuthorize wsAuthorize);

}
