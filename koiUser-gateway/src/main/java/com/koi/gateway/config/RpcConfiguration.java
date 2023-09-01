package com.koi.gateway.config;

import com.koi.interfacer.api.InterfaceInfoApi;
import com.koi.interfacer.api.UserKeyPairApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * rpc 配置
 *
 * @Author zjl
 * @Date 2023/8/29 11:49
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {UserKeyPairApi.class, InterfaceInfoApi.class})
public class RpcConfiguration {
}
