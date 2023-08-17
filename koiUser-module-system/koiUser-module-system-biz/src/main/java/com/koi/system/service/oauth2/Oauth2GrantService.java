package com.koi.system.service.oauth2;

import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;

import java.util.List;

/**
 * OAuth2 授予 Service 接口
 *
 * @Author zjl
 * @Date 2023/7/30 17:25
 */
public interface Oauth2GrantService {

    /**
     * 授权码模式，第一阶段，获得 code 授权码
     *
     * 对应 Spring Security OAuth2 的 AuthorizationEndpoint 的 generateCode 方法
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @param redirectUri 重定向 URI
     * @param state 状态
     * @return 授权码
     */
    String grantAuthorizationCodeForCode(Long userId, Integer userType,
                                         String clientId, List<String> scopes,
                                         String redirectUri, String state);

    /**
     * 授权码模式，第二阶段，获得 accessToken 访问令牌
     *
     * 对应 Spring Security OAuth2 的 AuthorizationCodeTokenGranter 功能
     *
     * @param clientId 客户端编号
     * @param code 授权码
     * @param redirectUri 重定向 URI
     * @param state 状态
     * @return 访问令牌
     */
    Oauth2AccessToken grantAuthorizationCodeForAccessToken(String clientId, String code,
                                                           String redirectUri, String state);

    /**
     * 刷新模式
     *
     * 对应 Spring Security OAuth2 的 ResourceOwnerPasswordTokenGranter 功能
     *
     * @param refreshToken 刷新令牌
     * @param clientId 客户端编号
     * @return 访问令牌
     */
    Oauth2AccessToken grantRefreshToken(String refreshToken, String clientId);
}
