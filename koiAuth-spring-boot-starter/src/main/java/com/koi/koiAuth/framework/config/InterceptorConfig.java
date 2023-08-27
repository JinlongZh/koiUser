package com.koi.koiAuth.framework.config;

import com.koi.koiAuth.framework.core.filter.TokenAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置所有拦截器
 *
 * @Author zjl
 * @Date 2023/8/27 17:16
 */
@AutoConfiguration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAuthenticationFilter)
                .addPathPatterns("/**");
    }
}
