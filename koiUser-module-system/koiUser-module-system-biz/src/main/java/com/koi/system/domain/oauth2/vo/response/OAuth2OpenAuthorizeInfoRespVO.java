package com.koi.system.domain.oauth2.vo.response;

import com.koi.common.core.KeyValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 授权页的信息
 *
 * @Author zjl
 * @Date 2023/8/9 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2OpenAuthorizeInfoRespVO {

    /**
     * 客户端
     */
    private Client client;

    /**
     * scope 的选中信息,使用 List 保证有序性，Key 是 scope，Value 为是否选中
     */
    private List<KeyValue<String, Boolean>> scopes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client {

        /**
         * 应用名
         */
        private String name;

        /**
         * 应用图标
         */
        private String logo;

    }

}
