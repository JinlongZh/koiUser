package com.koi.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置属性
 *
 * @Author zjl
 * @Date 2024/1/21 18:26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
public class QiniuConfigProperties {

    /**
     * 域名
     */
    private String url;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucket名称
     */
    private String bucket;

    /**
     * 下载domain
     */
    private String domain;

}
