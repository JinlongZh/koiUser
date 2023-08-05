package com.koi.system.service.oauth2;

import com.koi.system.domain.oauth2.entity.Oauth2Code;

import java.util.List;

/**
 * OAuth2.0 授权码 Service 接口
 *
 * @Author zjl
 * @Date 2023/7/30 17:30
 */
public interface Oauth2CodeService {

    /**
     * 创建授权码
     *
     * 参考 JdbcAuthorizationCodeServices 的 createAuthorizationCode 方法
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @param redirectUri 重定向 URI
     * @param state 状态
     * @return 授权码的信息
     */
    Oauth2Code createAuthorizationCode(Long userId, Integer userType, String clientId,
                                       List<String> scopes, String redirectUri, String state);

    /**
     * 使用授权码
     *
     * @param code 授权码
     */
    Oauth2Code consumeAuthorizationCode(String code);

}
