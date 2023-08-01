package com.koi.system.api.oauth2.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * OAuth2.0 Token API 接口
 *
 * @Author zjl
 * @Date 2023/8/1 21:08
 */
@Data
public class OAuth2AccessTokenCheckRespDTO implements Serializable {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 授权范围的数组
     */
    private List<String> scopes;

}
