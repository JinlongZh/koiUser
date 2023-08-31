package com.koi.interfacer.provider.framework.config;

import com.koi.framework.web.core.handle.GlobalExceptionHandler;
import com.koi.interfacer.api.UserKeyPairApi;
import com.koi.interfacer.provider.framework.core.filter.InterfaceAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 拦截器配置类
 *
 * @Author zjl
 * @Date 2023/8/31 11:07
 */
@Configuration
public class FilterConfiguration {

    @Resource
    private UserKeyPairApi userKeyPairApi;
    @Resource
    private GlobalExceptionHandler globalExceptionHandler;


    /**
     * 配置 Token 过滤器
     */
    @Bean
    public FilterRegistrationBean<InterfaceAuthenticationFilter> flowableWebFilter() {
        FilterRegistrationBean<InterfaceAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new InterfaceAuthenticationFilter(userKeyPairApi, globalExceptionHandler));
        return registrationBean;
    }

}
