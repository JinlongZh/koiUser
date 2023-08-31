package com.koi.interfacer.provider.framework.core.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.sign.SignUtils;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.framework.web.core.handle.GlobalExceptionHandler;
import com.koi.interfacer.api.UserKeyPairApi;
import com.koi.interfacer.api.dto.response.UserKeyPairRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 接口验证拦截器
 *
 * @Author zjl
 * @Date 2023/8/31 11:11
 */
@Slf4j
@RequiredArgsConstructor
public class InterfaceAuthenticationFilter extends OncePerRequestFilter {

    private final UserKeyPairApi userKeyPairApi;

    private final GlobalExceptionHandler globalExceptionHandler;

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
        // 通过 accessKey 查询是否存在该用户秘钥对
        UserKeyPairRespDTO userKeyPairResp = null;
        try {
            userKeyPairResp = userKeyPairApi.getUserKeyPairByAccessKey(accessKey).getData();
        } catch (Exception e) {
            globalExceptionHandler.serviceExceptionHandler(new ServiceException(INTERNAL_SERVER_ERROR));
        }
        if (userKeyPairResp == null) {
            globalExceptionHandler.serviceExceptionHandler(new ServiceException(BAD_REQUEST.getCode(), "公钥或秘钥错误"));
        }

        // 判断随机数是否存在，防止重放攻击
        String existNonce = RedisUtils.getStr(nonce);
        if (StringUtils.isNotBlank(existNonce)) {
            globalExceptionHandler.serviceExceptionHandler(new ServiceException(BAD_REQUEST.getCode(), "请求重复"));
        }

        // 时间戳 和 当前时间不能超过 5 分钟 (300000毫秒)
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long difference = currentTimeMillis - Long.parseLong(timestamp);
        if (Math.abs(difference) > 300000) {
            globalExceptionHandler.serviceExceptionHandler(new ServiceException(BAD_REQUEST.getCode(), "请求超时"));
        }

        // 校验签名
        assert userKeyPairResp != null;
        String serverSign = SignUtils.genSign(body, userKeyPairResp.getSecretKey());
        if (!sign.equals(serverSign)) {
            globalExceptionHandler.serviceExceptionHandler(new ServiceException(BAD_REQUEST.getCode(), "公钥或秘钥错误"));
        }

        // 4. 判断请求的模拟接口是否存在

        // 5. 请求转发，调用模拟接口
        // 继续过滤链
        filterChain.doFilter(request, response);

        //
        System.out.println(response.getStatus());
    }
}
