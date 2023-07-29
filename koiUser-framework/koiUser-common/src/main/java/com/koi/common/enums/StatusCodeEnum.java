package com.koi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口状态枚举
 *
 * @Author zjl
 * @Date 2023/7/27 16:25
 */
@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS(200, "操作成功"),
    INVALID_REQUEST(400,"参数错误"),
    UNAUTHORIZED(401,"没有权限"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"资源不存在"),
    NOT_ACCEPTABLE(406,"请求的格式不正确"),
    INTERNAL_SERVER_ERROR(500,"服务器发生错误"),
    FAIL(501,"操作失败"),

    USERNAME_OR_PASSWORD_ERR(2000,"用户未登录或token已失效"),
    AUTH_EXPIRED(3000,"认证到期"),
    TOKEN_ERR(3001, "token无效");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

}
