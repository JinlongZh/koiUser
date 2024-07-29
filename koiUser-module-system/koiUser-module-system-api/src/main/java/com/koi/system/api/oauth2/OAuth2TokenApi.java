package com.koi.system.api.oauth2;

import com.koi.system.api.oauth2.dto.request.OAuth2AccessTokenCreateReqDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;

import javax.validation.Valid;

/**
 * OAuth2.0 Token API 接口
 *
 * @Author zjl
 * @Date 2023/8/1 21:04
 */
public interface OAuth2TokenApi {

    /**
     * 创建访问令牌
     *
     * @param reqDTO 访问令牌的创建信息
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenRespDTO createAccessToken(@Valid OAuth2AccessTokenCreateReqDTO reqDTO);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenRespDTO removeAccessToken(String accessToken);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @param clientId 客户端编号
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId);

}
