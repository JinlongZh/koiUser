package com.koi.interfacer.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 开放接口 应用配置
 *
 * @Author zjl
 * @Date 2023/8/29 16:05
 */
@ConfigurationProperties(prefix = "koiuser.interfacer")
@Validated
@Data
public class KoiUserInterfacerProperties {

    /**
     * 公钥
     */
    @NotEmpty(message = "accessKey 不能为空")
    private String accessKey = "test";

    /**
     * 秘钥
     */
    @NotEmpty(message = "secretKey 不能为空")
    private String secretKey = "test";

}
