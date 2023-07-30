package com.koi.system.oauth2.service;

import com.koi.system.oauth2.domain.entity.Oauth2AccessToken;

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

}