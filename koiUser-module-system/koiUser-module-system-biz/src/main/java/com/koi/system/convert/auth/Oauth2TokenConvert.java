package com.koi.system.convert.auth;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;

import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/5 20:59
 */
public interface Oauth2TokenConvert {


    static OAuth2AccessTokenCheckRespDTO convertAccessTokenCheck(Oauth2AccessToken bean) {
        OAuth2AccessTokenCheckRespDTO respDTO = BeanCopyUtils.copyObject(bean, OAuth2AccessTokenCheckRespDTO.class);
        respDTO.setScopes(stringListFromJson(bean.getScopes()));
        return respDTO;
    }

    static OAuth2AccessTokenRespDTO convertAccessToken(Oauth2AccessToken bean) {
        return BeanCopyUtils.copyObject(bean, OAuth2AccessTokenRespDTO.class);
    }

}
