package com.koi.koiAuth.framework.config;

import com.koi.koiAuth.client.OAuth2Client;
import com.koi.koiAuth.client.UserClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 自动配置类
 *
 * @Author zjl
 * @Date 2023/8/27 21:14
 */
@AutoConfiguration
@EnableConfigurationProperties(KoiAuthProperties.class)
public class KoiAuthClientAutoConfiguration {

    @Resource
    private KoiAuthProperties koiAuthProperties;

    @Bean
    public OAuth2Client oauth2Client() {
        return new OAuth2Client(koiAuthProperties);
    }

    @Bean
    public UserClient userClient() {
        return new UserClient();
    }
}
