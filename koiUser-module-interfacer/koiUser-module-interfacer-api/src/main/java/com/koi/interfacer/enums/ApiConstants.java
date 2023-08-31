package com.koi.interfacer.enums;

import com.koi.common.enums.RpcConstants;

/**
 * API 相关的枚举
 *
 * @Author zjl
 * @Date 2023/8/31 14:41
 */
public class ApiConstants {

    /**
     * 服务名
     *
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "interfacer-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX +  "/interfacer";

}
