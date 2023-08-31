package com.koi.gateway.filter.interfacer;

import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/31 21:32
 */
public class InterfacerGatewayFilter implements GatewayFilter, Ordered {
    private final static String BEAGIN = "begin";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(BEAGIN, System.currentTimeMillis());

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    long startTime = exchange.getAttribute(BEAGIN);
                    long endTime = System.currentTimeMillis();
                    String url = exchange.getRequest().getURI().getRawPath();
                    System.out.println(StrUtil.format("{}耗时:{}", url, endTime - startTime));

                })

        );
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
