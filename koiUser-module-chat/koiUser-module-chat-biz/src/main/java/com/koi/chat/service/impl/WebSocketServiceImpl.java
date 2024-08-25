package com.koi.chat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.koi.chat.config.ThreadPoolConfig;
import com.koi.chat.domain.dto.WSAuthorize;
import com.koi.chat.domain.dto.WSBaseRespDTO;
import com.koi.chat.domain.dto.WSChannelExtraDTO;
import com.koi.chat.service.WebSocketService;
import com.koi.chat.websocket.NettyUtil;
import com.koi.framework.security.core.LoginUser;
import com.koi.system.api.oauth2.OAuth2TokenApi;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * websocket 服务实现类
 *
 * @Author zjl
 * @Date 2024/8/3
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Resource
    private OAuth2TokenApi tokenService;
    @Resource(name = ThreadPoolConfig.WS_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 所有已连接的websocket连接列表和一些额外参数
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();
    /**
     * 所有在线的用户和对应的socket
     */
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ONLINE_USERID_MAP = new ConcurrentHashMap<>();

    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @Override
    public void removed(Channel channel) {
        WSChannelExtraDTO wsChannelExtraDTO = ONLINE_WS_MAP.get(channel);
        Optional<Long> uidOptional = Optional.ofNullable(wsChannelExtraDTO)
                .map(WSChannelExtraDTO::getUserId);
        offline(channel, uidOptional);
    }

    /**
     * 用户下线
     * return 是否全下线成功
     */
    private boolean offline(Channel channel, Optional<Long> userIdOptional) {
        ONLINE_WS_MAP.remove(channel);
        if (userIdOptional.isPresent()) {
            CopyOnWriteArrayList<Channel> channels = ONLINE_USERID_MAP.get(userIdOptional.get());
            if (CollectionUtil.isNotEmpty(channels)) {
                channels.removeIf(ch -> Objects.equals(ch, channel));
            }
            return CollectionUtil.isEmpty(ONLINE_USERID_MAP.get(userIdOptional.get()));
        }
        return true;
    }

    @Override
    public void sendToUserId(WSBaseRespDTO<?> wsBaseResp, Long uid) {
        CopyOnWriteArrayList<Channel> channels = ONLINE_USERID_MAP.get(uid);
        if (CollectionUtil.isEmpty(channels)) {
            log.info("用户：{}不在线", uid);
            return;
        }
        channels.forEach(channel -> {
            threadPoolTaskExecutor.execute(() -> sendMsg(channel, wsBaseResp));
        });
    }

    @Override
    public void sendToAllOnline(WSBaseRespDTO<?> wsBaseResp, Long skipUserId) {
        ONLINE_WS_MAP.forEach((channel, ext) -> {
            if (Objects.nonNull(skipUserId) && Objects.equals(ext.getUserId(), skipUserId)) {
                return;
            }
            threadPoolTaskExecutor.execute(() -> sendMsg(channel, wsBaseResp));
        });
    }

    @Override
    public void sendToAllOnline(WSBaseRespDTO<?> wsBaseResp) {
        sendToAllOnline(wsBaseResp, null);
    }

    @Override
    public void authorize(Channel channel, WSAuthorize wsAuthorize) {
        // 校验token
        OAuth2AccessTokenCheckRespDTO oAuth2AccessTokenCheckRespDTO = tokenService.checkAccessToken(wsAuthorize.getToken());
        if (oAuth2AccessTokenCheckRespDTO != null) {
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId(oAuth2AccessTokenCheckRespDTO.getUserId());
            loginUser.setUserType(oAuth2AccessTokenCheckRespDTO.getUserType());
            loginUser.setScopes(oAuth2AccessTokenCheckRespDTO.getScopes());

            loginSuccess(channel, loginUser, wsAuthorize.getToken());
        }

    }

    private void loginSuccess(Channel channel, LoginUser loginUser, String token) {
        // 更新上线列表
        online(channel, loginUser.getUserId());
    }

    /**
     * 用户上线
     */
    private void online(Channel channel, Long userId) {
        // 给已连接的websocket设置额外信息
        getOrInitChannelExt(channel).setUserId(userId);
        // 设置在线的用户和对应的socket
        ONLINE_USERID_MAP.putIfAbsent(userId, new CopyOnWriteArrayList<>());
        ONLINE_USERID_MAP.get(userId).add(channel);
        NettyUtil.setAttr(channel, NettyUtil.USER_ID, userId);
    }

    private WSChannelExtraDTO getOrInitChannelExt(Channel channel) {
        WSChannelExtraDTO wsChannelExtraDTO =
                ONLINE_WS_MAP.getOrDefault(channel, new WSChannelExtraDTO());
        WSChannelExtraDTO old = ONLINE_WS_MAP.putIfAbsent(channel, wsChannelExtraDTO);
        return ObjectUtil.isNull(old) ? wsChannelExtraDTO : old;
    }


    /**
     * 给本地channel发送消息
     *
     * @param channel
     * @param wsBaseResp
     */
    private void sendMsg(Channel channel, WSBaseRespDTO<?> wsBaseResp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsBaseResp)));
    }

}
