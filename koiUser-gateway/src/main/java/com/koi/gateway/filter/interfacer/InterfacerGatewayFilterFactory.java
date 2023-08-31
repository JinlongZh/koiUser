package com.koi.gateway.filter.interfacer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/31 22:08
 */
@Component
public class InterfacerGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

//    public InterfacerGatewayFilterFactory() {
//        super(Config.class);
//    }

    @Override
    public GatewayFilter apply(Object config) {
        return new InterfacerGatewayFilter();
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class Config {
//        private String message;
//    }
}
