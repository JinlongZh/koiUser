package com.koi.system.framework.config.rpc;

import com.koi.member.api.user.MemberUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/10 9:46
 */
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = MemberUserApi.class)
public class RpcConfiguration {
}
