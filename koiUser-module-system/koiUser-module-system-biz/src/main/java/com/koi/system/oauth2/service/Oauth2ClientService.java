package com.koi.system.oauth2.service;

import com.koi.system.oauth2.domain.entity.Oauth2Client;

import java.util.Collection;

/**
 * 提供客户端的操作
 *
 * @Author zjl
 * @Date 2023/7/30 16:41
 */
public interface Oauth2ClientService {

    /**
     * 初始化 OAuth2Client 的本地缓存
     */
    void initLocalCache();

    /**
     * 从缓存中，校验客户端是否合法
     *
     * 非空时，进行校验
     *
     * @param clientId 客户端编号
     * @param clientSecret 客户端密钥
     * @param authorizedGrantType 授权方式
     * @param scopes 授权范围
     * @param redirectUri 重定向地址
     * @return 客户端
     */
    Oauth2Client validOAuthClientFromCache(String clientId, String clientSecret,
                                           String authorizedGrantType, Collection<String> scopes, String redirectUri);

    default Oauth2Client validOAuthClientFromCache(String clientId) {
        return validOAuthClientFromCache(clientId, null, null, null, null);
    }

}
