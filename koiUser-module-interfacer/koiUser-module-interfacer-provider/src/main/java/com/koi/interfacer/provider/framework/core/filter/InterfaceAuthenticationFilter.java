package com.koi.interfacer.provider.framework.core.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.koi.common.domain.CommonResult;
import com.koi.common.exception.ServiceException;
import com.koi.interfacer.api.UserKeyPairApi;
import com.koi.interfacer.api.dto.response.UserKeyPairRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * 接口验证拦截器
 *
 * @Author zjl
 * @Date 2023/8/31 11:11
 */
@Slf4j
@Component
public class InterfaceAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 请求日志

        // 2. 黑白名单


        // 3. 用户鉴权 （判断 accessKey 和 secretKey 是否合法）
        String accessKey = request.getHeader("accessKey");
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        String method = request.getHeader("method");

        if (StringUtils.isEmpty(nonce)
                || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(timestamp)
                || StringUtils.isEmpty(method)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "请求头参数不完整！");
        }
        // 通过 accessKey 查询是否存在该用户

        // 判断随机数是否存在，防止重放攻击


        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
