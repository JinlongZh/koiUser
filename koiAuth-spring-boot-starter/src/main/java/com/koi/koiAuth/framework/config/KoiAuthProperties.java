package com.koi.koiAuth.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * oauth2 应用配置
 *
 * @Author zjl
 * @Date 2023/8/27 20:30
 */
@ConfigurationProperties(prefix = "koiauth.oauth2")
@Validated
@Data
public class KoiAuthProperties {

    /**
     * 应用id
     */
    @NotEmpty(message = "clientId 不能为空")
    private String clientId = "testId";

    /**
     * 应用秘钥
     */
    @NotEmpty(message = "clientSecret 不能为空")
    private String clientSecret = "testSecret";

}
