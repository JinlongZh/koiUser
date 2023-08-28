package com.koi.koiAuth.framework.core.utils;

import com.koi.koiAuth.framework.core.dto.LoginUser;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/27 12:42
 */
public class KoiAuthSecurityUtils {

    public static final String AUTHORIZATION_BEARER = "Bearer";

    /**
     * 从请求中，获得认证 Token
     *
     * @param request 请求
     * @param header 认证 Token 对应的 Header 名字
     * @return 认证 Token
     */
    public static String obtainAuthorization(HttpServletRequest request, String header) {
        String authorization = request.getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) { // 未找到
            return null;
        }
        return authorization.substring(index + 7).trim();
    }

    /**
     * 获取当前用户, 从上下文中
     *
     * @return 当前用户
     */
    @Nullable
    public static LoginUser getLoginUser() {
        return RequestHolder.get();
    }

    /**
     * 获得当前用户的编号，从上下文中
     *
     * @return 用户编号
     */
    @Nullable
    public static Long getLoginUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getId() : null;
    }
}
