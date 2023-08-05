package com.koi.system.service.oauth2;

import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;

import java.util.List;

/**
 *
 *
 * @Author zjl
 * @Date 2023/7/30 16:43
 */
public interface Oauth2TokenService {

    /**
     * 创建访问令牌，包含创建刷新令牌的创建
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param clientId 客户端编号
     * @param scopes 授权范围
     * @return 访问令牌的信息
     */
    Oauth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    Oauth2AccessToken checkAccessToken(String accessToken);

    /**
     * 获得访问令牌
     *
     * 参考 DefaultTokenServices 的 getAccessToken 方法
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    Oauth2AccessToken getAccessToken(String accessToken);

}
