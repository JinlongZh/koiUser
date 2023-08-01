package com.koi.system.oauth2.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.koi.common.exception.ServiceException;
import com.koi.system.oauth2.domain.entity.Oauth2AccessToken;
import com.koi.system.oauth2.domain.entity.Oauth2Code;
import com.koi.system.oauth2.service.Oauth2CodeService;
import com.koi.system.oauth2.service.Oauth2GrantService;
import com.koi.system.oauth2.service.Oauth2TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * OAuth2 授予 Service 实现类
 *
 * @Author zjl
 * @Date 2023/7/30 17:25
 */
@Service
public class Oauth2GrantServiceImpl implements Oauth2GrantService {

    @Resource
    private Oauth2TokenService oauth2TokenService;
    @Resource
    private Oauth2CodeService oauth2CodeService;

    @Override
    public String grantAuthorizationCodeForCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUri, String state) {
        return oauth2CodeService.createAuthorizationCode(userId, userType, clientId, scopes,
                redirectUri, state).getCode();
    }

    @Override
    public Oauth2AccessToken grantAuthorizationCodeForAccessToken(String clientId, String code, String redirectUri, String state) {
        Oauth2Code oauth2Code = oauth2CodeService.consumeAuthorizationCode(code);
        Assert.notNull(oauth2Code, "授权码不能为空"); // 防御性编程
        // 校验 clientId 是否匹配
        if (!StrUtil.equals(clientId, oauth2Code.getClientId())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "client_id 不匹配");
        }
        // 校验 redirectUri 是否匹配
        if (!StrUtil.equals(redirectUri, oauth2Code.getRedirectUri())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "redirect_uri 不匹配");
        }
        // 校验 state 是否匹配
        state = StrUtil.nullToDefault(state, ""); // 数据库 state 为 null 时，会设置为 "" 空串
        if (!StrUtil.equals(state, oauth2Code.getState())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "state 不匹配");
        }

        // 创建访问令牌
        return oauth2TokenService.createAccessToken(oauth2Code.getUserId(), oauth2Code.getUserType(),
                oauth2Code.getClientId(), stringListFromJson(oauth2Code.getScopes()));
    }
}
