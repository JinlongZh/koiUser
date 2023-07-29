package com.koi.koiuserserver.handler;

import com.koi.common.pojo.CommonResult;
import com.koi.koiuserserver.utils.SecurityUtils;
import com.koi.koiuserserver.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问一个需要认证的 URL 资源，已经认证（登录）但是没有权限的情况下返回信息
 *
 * @Author zjl
 * @Date 2023/7/27 16:19
 */
@Component
@SuppressWarnings("JavadocReference")
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        // 打印 warn 的原因是，不定期合并 warn，看看有没恶意破坏
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(),
                SecurityUtils.getLoginUserId(), e);
        // 返回 403
        CommonResult<Object> result = new CommonResult<>();
        result.setCode(HttpStatus.FORBIDDEN.value());
        result.setMsg("没有该操作权限");
        ServletUtils.writeJSON(response, result);
    }

}
