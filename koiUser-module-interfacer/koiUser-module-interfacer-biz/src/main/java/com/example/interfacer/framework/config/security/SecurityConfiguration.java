package com.example.interfacer.framework.config.security;

import com.koi.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * interfacer 模块的 Security 配置
 *
 * @Author zjl
 * @Date 2023/8/29 11:50
 */
@Configuration(proxyBeanMethods = false, value = "interfacerSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("interfacerAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger 接口文档
                registry.antMatchers("/v3/api-docs/**").permitAll() // 元数据
                        .antMatchers("/swagger-ui.html").permitAll(); // Swagger UI
                // RPC 服务的安全配置
//                registry.antMatchers(ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
