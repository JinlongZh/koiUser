package com.koi.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * websocket推送类型枚举
 *
 * @Author zjl
 * @Date 2023/10/13 18:22
 */
@AllArgsConstructor
@Getter
public enum WebSocketPushTypeEnum {

    USER(1, "个人"),
    ALL(2, "全部连接用户"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, WebSocketPushTypeEnum> cache;

    static {
        cache = Arrays.stream(WebSocketPushTypeEnum.values()).collect(Collectors.toMap(WebSocketPushTypeEnum::getType, Function.identity()));
    }

    public static WebSocketPushTypeEnum of(Integer type) {
        return cache.get(type);
    }

}
