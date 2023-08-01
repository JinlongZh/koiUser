package com.koi.system.api.oauth2;

import com.koi.system.api.oauth2.dto.request.OAuth2AccessTokenCreateReqDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;
import org.springframework.stereotype.Service;

/**
 * OAuth2.0 Token API 实现类
 *
 * @Author zjl
 * @Date 2023/8/1 21:30
 */
@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi{


    @Override
    public OAuth2AccessTokenRespDTO createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        return null;
    }

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        return null;
    }

    @Override
    public OAuth2AccessTokenRespDTO removeAccessToken(String accessToken) {
        return null;
    }

    @Override
    public OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId) {
        return null;
    }
}
