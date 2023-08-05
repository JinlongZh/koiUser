package com.koi.framework.security.core.rpc;

import com.koi.framework.rpc.core.utils.FeignUtils;
import com.koi.framework.security.core.LoginUser;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign 请求时，将 {@link LoginUser} 设置到 header 中，继续透传给被调用的服务
 *
 * @Author zjl
 * @Date 2023/8/5 17:31
 */
public class LoginUserRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user != null) {
            FeignUtils.createJsonHeader(requestTemplate, SecurityFrameworkUtils.LOGIN_USER_HEADER, user);
        }
    }

}
