package com.koi.interfacer.framework.config.rpc;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * rpc 配置
 *
 * @Author zjl
 * @Date 2023/8/29 11:49
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients()
public class RpcConfiguration {
}
