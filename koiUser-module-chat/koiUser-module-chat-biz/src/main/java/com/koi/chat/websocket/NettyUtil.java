package com.koi.chat.websocket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * netty工具类
 *
 * @Author zjl
 * @Date 2024/8/3
 */
public class NettyUtil {

    public static AttributeKey<String> TOKEN = AttributeKey.valueOf("Authorization");
    public static AttributeKey<String> IP = AttributeKey.valueOf("ip");
    public static AttributeKey<Long> USER_ID = AttributeKey.valueOf("userId");
    public static AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");

    public static <T> void setAttr(Channel channel, AttributeKey<T> attributeKey, T data) {
        Attribute<T> attr = channel.attr(attributeKey);
        attr.set(data);
    }

    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        return channel.attr(attributeKey).get();
    }

}
