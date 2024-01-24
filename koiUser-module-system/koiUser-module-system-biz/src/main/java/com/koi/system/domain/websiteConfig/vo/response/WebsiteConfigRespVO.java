package com.koi.system.domain.websiteConfig.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 网站配置
 *
 * @Author zjl
 * @Date 2024/1/24 20:59
 */
@Data
public class WebsiteConfigRespVO {

    /**
     * id
     */
    private Long id;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（0是 1否）
     */
    private Integer configType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
