package com.koi.interfacer.framework.config;

import com.koi.interfacer.client.InterfacerClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 自动配置类
 *
 * @Author zjl
 * @Date 2023/8/29 16:03
 */
@AutoConfiguration
@EnableConfigurationProperties(KoiUserInterfacerProperties.class)
public class KoiUserInterfacerAutoConfiguration {

    @Resource
    private KoiUserInterfacerProperties koiUserInterfacerProperties;

    @Bean
    public InterfacerClient interfacerClient() {
        return new InterfacerClient(koiUserInterfacerProperties);
    }

}
