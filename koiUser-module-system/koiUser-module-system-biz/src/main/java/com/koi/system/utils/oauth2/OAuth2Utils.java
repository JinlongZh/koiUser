package com.koi.system.utils.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.koi.common.utils.http.HttpUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/30 16:01
 */
public class OAuth2Utils {

    /**
     * 构建授权码模式下，重定向的 URI
     *
     * copy from Spring Security OAuth2 的 AuthorizationEndpoint 类的 getSuccessfulRedirect 方法
     *
     * @param redirectUri 重定向 URI
     * @param authorizationCode 授权码
     * @param state 状态
     * @return 授权码模式下的重定向 URI
     */
    public static String buildAuthorizationCodeRedirectUri(String redirectUri, String authorizationCode, String state) {
        Map<String, String> query = new LinkedHashMap<>();
        query.put("code", authorizationCode);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, false);
    }

    public static String buildUnsuccessfulRedirect(String redirectUri, String responseType, String state,
                                                   String error, String description) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("error", error);
        query.put("error_description", description);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, !responseType.contains("code"));
    }

    public static long getExpiresIn(LocalDateTime expireTime) {
        return LocalDateTimeUtil.between(LocalDateTime.now(), expireTime, ChronoUnit.SECONDS);
    }

    public static String buildScopeStr(Collection<String> scopes) {
        return CollUtil.join(scopes, " ");
    }

    public static List<String> buildScopes(String scope) {
        return StrUtil.split(scope, ' ');
    }

}
