package com.koi.koiuserserver.handler;

import com.koi.common.result.Result;
import com.koi.koiuserserver.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，返回错误码，从而使前端重定向到登录页
 *
 * @Author zjl
 * @Date 2023/7/27 16:19
 */
@Component
@Slf4j
@SuppressWarnings("JavadocReference") // 忽略文档引用报错
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.debug("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
        // 返回 401
        Result<Object> result = new Result<>();
        result.setCode(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("账号未登录");
        ServletUtils.writeJSON(response, result);
    }

}
