package com.koi.koiuserserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/27 16:02
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 设置 URL 安全权限
        httpSecurity.csrf().disable() // 禁用 CSRF 保护
                .authorizeRequests()
                // 1. 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // 2. 登录相关的接口，可匿名访问
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                // last. 兜底规则，必须认证
                .and().authorizeRequests()
                .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
