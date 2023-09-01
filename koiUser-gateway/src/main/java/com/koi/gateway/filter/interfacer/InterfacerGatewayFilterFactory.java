package com.koi.gateway.filter.interfacer;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/31 22:08
 */
@Component
public class InterfacerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    InterfacerGatewayFilter interfacerGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return interfacerGatewayFilter;
    }

}
