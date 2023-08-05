package com.koi.system.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型枚举
 *
 * @Author zjl
 * @Date 2023/8/4 21:46
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN_USERNAME(100), // 使用账号登录
    LOGIN_SOCIAL(101), // 使用社交登录
    LOGIN_MOBILE(103), // 使用手机登陆
    LOGIN_SMS(104), // 使用短信登陆

    LOGOUT_SELF(200),  // 自己主动登出
    LOGOUT_DELETE(202), // 强制退出
    ;

    /**
     * 日志类型
     */
    private final Integer type;

}