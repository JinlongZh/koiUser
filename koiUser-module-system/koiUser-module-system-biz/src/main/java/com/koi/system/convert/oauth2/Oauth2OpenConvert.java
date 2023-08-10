package com.koi.system.convert.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.koi.common.core.KeyValue;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.oauth2.entity.Oauth2Approve;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAccessTokenRespVO;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAuthorizeInfoRespVO;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenCheckTokenRespVO;
import com.koi.system.utils.oauth2.OAuth2Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/8/1 16:21
 */
public interface Oauth2OpenConvert {

    static OAuth2OpenAccessTokenRespVO convertAccessToken(Oauth2AccessToken bean) {
        OAuth2OpenAccessTokenRespVO respVO = BeanCopyUtils.copyObject(bean, OAuth2OpenAccessTokenRespVO.class);
        respVO.setTokenType(SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        respVO.setExpiresIn(OAuth2Utils.getExpiresIn(bean.getExpiresTime()));
        respVO.setScope(OAuth2Utils.buildScopeStr(stringListFromJson(bean.getScopes())));
        return respVO;
    }

    static OAuth2OpenCheckTokenRespVO convertCheckToken(Oauth2AccessToken bean) {
        OAuth2OpenCheckTokenRespVO respVO = BeanCopyUtils.copyObject(bean, OAuth2OpenCheckTokenRespVO.class);
        respVO.setScopes(stringListFromJson(bean.getScopes()));
        respVO.setExp(LocalDateTimeUtil.toEpochMilli(bean.getExpiresTime()) / 1000L);
        respVO.setUserType(UserTypeEnum.MEMBER.getValue());
        return respVO;
    }

    static OAuth2OpenAuthorizeInfoRespVO convertOpenAuthorizeInfo(Oauth2Client client, List<Oauth2Approve> approves) {
        // 构建 scopes
        List<String> clientScopeList = stringListFromJson(client.getScopes());
        List<KeyValue<String, Boolean>> scopesResp = new ArrayList<>(clientScopeList.size());
        Map<String, Oauth2Approve> approveMap = CollectionUtils.convertMap(approves, Oauth2Approve::getScope);
        clientScopeList.forEach(scope -> {
            Oauth2Approve approve = approveMap.get(scope);
            scopesResp.add(new KeyValue<>(scope, approve != null ? approve.getApproved() : false));
        });
        // 拼接返回
        return new OAuth2OpenAuthorizeInfoRespVO(
                new OAuth2OpenAuthorizeInfoRespVO.Client(client.getName(), client.getLogo()),
                scopesResp
        );
    }
}
