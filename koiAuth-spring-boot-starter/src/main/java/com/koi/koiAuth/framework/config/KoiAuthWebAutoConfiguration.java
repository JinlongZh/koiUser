package com.koi.koiAuth.framework.config;

import com.koi.common.enums.WebFilterOrderEnum;
import com.koi.koiAuth.framework.core.filter.TokenAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 配置所有拦截器
 *
 * @Author zjl
 * @Date 2023/8/27 17:16
 */
@AutoConfiguration
public class KoiAuthWebAutoConfiguration {

    /**
     * 配置 Token 过滤器
     */
    @Bean
    public FilterRegistrationBean<TokenAuthenticationFilter> flowableWebFilter() {
        FilterRegistrationBean<TokenAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenAuthenticationFilter());
        registrationBean.setOrder(WebFilterOrderEnum.OAUTH2_TOKEN_FILTER);
        return registrationBean;
    }

}
