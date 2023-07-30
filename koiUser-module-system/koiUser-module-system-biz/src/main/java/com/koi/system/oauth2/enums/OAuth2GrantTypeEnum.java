package com.koi.system.oauth2.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 授权类型（模式）的枚举
 *
 * @Author zjl
 * @Date 2023/7/30 16:19
 */
@AllArgsConstructor
@Getter
public enum OAuth2GrantTypeEnum {

    PASSWORD("password"), // 密码模式
    AUTHORIZATION_CODE("authorization_code"), // 授权码模式
    IMPLICIT("implicit"), // 简化模式
    CLIENT_CREDENTIALS("client_credentials"), // 客户端模式
    REFRESH_TOKEN("refresh_token"), // 刷新模式
    ;

    private final String grantType;

    public static OAuth2GrantTypeEnum getByGranType(String grantType) {
        return ArrayUtil.firstMatch(o -> o.getGrantType().equals(grantType), values());
    }

}
