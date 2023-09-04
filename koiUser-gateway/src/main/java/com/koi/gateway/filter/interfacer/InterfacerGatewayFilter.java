package com.koi.gateway.filter.interfacer;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.koi.common.domain.CommonResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.json.JsonUtils;
import com.koi.common.utils.sign.SignUtils;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.interfacer.api.InterfaceInfoApi;
import com.koi.interfacer.api.UserKeyPairApi;
import com.koi.interfacer.api.dto.response.InterfaceInfoRespDTO;
import com.koi.interfacer.api.dto.response.UserKeyPairRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/31 21:32
 */
@Slf4j
@Component
public class InterfacerGatewayFilter implements GatewayFilter, Ordered {

    private static final String DYE_DATA_HEADER = "X-Dye-Data";
    private static final String DYE_DATA_VALUE = "koi";

    private static final TypeReference<CommonResult<UserKeyPairRespDTO>> USER_KEY_PAIR_DTO
            = new TypeReference<CommonResult<UserKeyPairRespDTO>>() {
    };
    private static final TypeReference<CommonResult<InterfaceInfoRespDTO>> INTERFACE_INFO_DTO
            = new TypeReference<CommonResult<InterfaceInfoRespDTO>>() {
    };

    private final WebClient webClient;

    public InterfacerGatewayFilter(ReactorLoadBalancerExchangeFilterFunction lbFunction) {

        this.webClient = WebClient.builder().filter(lbFunction).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        // 1. 请求日志

        // 2. 黑白名单


        // 3. 用户鉴权 （判断 accessKey 和 secretKey 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String nonce = headers.getFirst("nonce");
        String sign = headers.getFirst("sign");
        String body = URLUtil.decode(headers.getFirst("body"), CharsetUtil.CHARSET_UTF_8);
        String method = headers.getFirst("method");

        if (StringUtils.isEmpty(nonce)
                || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(timestamp)
                || StringUtils.isEmpty(method)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "请求头参数不完整！");
        }
        // 判断随机数是否存在，防止重放攻击
        String existNonce = RedisUtils.getStr(nonce);
        if (StringUtils.isNotBlank(existNonce)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "请求重复");
        }

        // 时间戳 和 当前时间不能超过 5 分钟 (300000毫秒)
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long difference = currentTimeMillis - Long.parseLong(timestamp);
        if (Math.abs(difference) > 300000) {
            throw new ServiceException(BAD_REQUEST.getCode(), "请求超时");
        }

        // 通过 accessKey 查询是否存在该用户秘钥对
        return getUserKeyPairByAccessKey(accessKey).flatMap(userKeyPairResult -> {
            CommonResult<UserKeyPairRespDTO> result = JsonUtils.parseObject(userKeyPairResult, USER_KEY_PAIR_DTO);
            if (result.isError()) {
                return Mono.error(new ServiceException(INTERNAL_SERVER_ERROR.getCode(), "获取用户秘钥对失败"));
            }
            UserKeyPairRespDTO userKeyPairResp = result.getData();
            if (userKeyPairResp == null) {
                return Mono.error(new ServiceException(BAD_REQUEST.getCode(), "公钥或秘钥错误"));
            }

            // 校验签名
            String serverSign = SignUtils.genSign(body, userKeyPairResp.getSecretKey());
            if (!sign.equals(serverSign)) {
                return Mono.error(new ServiceException(BAD_REQUEST.getCode(), "公钥或秘钥错误"));
            }
            return processInterface(exchange, chain, path, method, userKeyPairResp);
        });
    }

    private Mono<Void> processInterface(ServerWebExchange exchange, GatewayFilterChain chain, String path, String method, UserKeyPairRespDTO userKeyPairResp) {
        // 4. 判断请求的接口是否存在
        return getInterfaceInfo(path, method).flatMap(interfaceInfoResult -> {
            CommonResult<InterfaceInfoRespDTO> result = JsonUtils.parseObject(interfaceInfoResult, INTERFACE_INFO_DTO);
            if (result.isError()) {
                return Mono.error(new ServiceException(INTERNAL_SERVER_ERROR.getCode(), "获取接口信息失败"));
            }
            InterfaceInfoRespDTO interfaceInfoResp = result.getData();
            return chain.filter(exchange);
        });
    }


    private Mono<String> getUserKeyPairByAccessKey(String accessKey) {
        return webClient.get()
                .uri(UserKeyPairApi.URL_KEYPAIR, uriBuilder -> uriBuilder.queryParam("accessKey", accessKey).build())
                .retrieve().bodyToMono(String.class);
    }

    private Mono<String> getInterfaceInfo(String path, String method) {
        return webClient.get()
                .uri(InterfaceInfoApi.URL_INTERFACE_INFO, uriBuilder -> uriBuilder
                        .queryParam("path", path)
                        .queryParam("method", method)
                        .build())
                .retrieve().bodyToMono(String.class);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
