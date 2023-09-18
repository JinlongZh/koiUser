package com.koi.framework.limitrate.core.enums;

import lombok.Getter;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:31
 */
@Getter
public enum LimitTypeEnum {

    /**
     * 单IP-令牌桶算法-指请求用户的IP
     */
    IP_TOKEN(0, "IP_TOKEN", "单IP-令牌桶算法"),
    /**
     * 单应用-令牌桶算法 -指部署的应用
     */
    APP_TOKEN(0, "APP_TOKEN", "单应用-令牌桶算法"),
    /**
     * 全局-令牌桶算法  -全部
     */
    GLOBAL_TOKEN(0, "GLOBAL_TOKEN", "全局-令牌桶算法"),
    /**
     * 单IP-漏桶算法-指请求用户的IP
     */
    IP_LEAKY(1, "IP_LEAKY", "单IP-漏桶算法"),
    /**
     * 单应用-漏桶算法 -指部署的应用
     */
    APP_LEAKY(1, "APP_LEAKY", "单应用-漏桶算法"),
    /**
     * 全局-漏桶算法  -全部
     */
    GLOBAL_LEAKY(1, "GLOBAL_LEAKY", "全局-漏桶算法"),
    ;
    private final int code;
    private final String name;
    private final String decr;

    LimitTypeEnum(int code, String name, String decr) {
        this.code = code;
        this.name = name;
        this.decr = decr;
    }

}
