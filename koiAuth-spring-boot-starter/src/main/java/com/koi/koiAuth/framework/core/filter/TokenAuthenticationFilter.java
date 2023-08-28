package com.koi.koiAuth.framework.core.filter;

import com.koi.common.domain.CommonResult;
import com.koi.koiAuth.client.OAuth2Client;
import com.koi.koiAuth.framework.core.dto.LoginUser;
import com.koi.koiAuth.framework.core.dto.oauth2.OAuth2CheckTokenRespDTO;
import com.koi.koiAuth.framework.core.utils.KoiAuthSecurityUtils;
import com.koi.koiAuth.framework.core.utils.RequestHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 过滤器
 *
 * @Author zjl
 * @Date 2023/8/27 12:20
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "AccessToken";

    @Resource
    private OAuth2Client oAuth2Client;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. 获得访问令牌
            String token = KoiAuthSecurityUtils.obtainAuthorization(request, TOKEN_HEADER);
            if (StringUtils.hasText(token)) {
                // 2. 基于 token 构建登录用户
                LoginUser loginUser = buildLoginUserByToken(token);
                // 3. 设置当前用户
                if (loginUser != null) {
                    RequestHolder.set(loginUser);
                }
            }
            // 继续过滤链
            filterChain.doFilter(request, response);
        } finally {
            RequestHolder.remove();
        }
    }

    private LoginUser buildLoginUserByToken(String token) {
        try {
            CommonResult<OAuth2CheckTokenRespDTO> accessTokenResult = oAuth2Client.checkToken(token);
            OAuth2CheckTokenRespDTO accessToken = accessTokenResult.getData();
            if (accessToken == null) {
                return null;
            }
            // 构建登录用户
            return new LoginUser()
                    .setId(accessToken.getUserId())
                    .setUserType(accessToken.getUserType())
                    .setScopes(accessToken.getScopes())
                    .setAccessToken(accessToken.getAccessToken());
        } catch (Exception exception) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
