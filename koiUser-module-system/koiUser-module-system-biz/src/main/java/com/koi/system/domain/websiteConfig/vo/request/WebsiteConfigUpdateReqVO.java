package com.koi.system.domain.websiteConfig.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加网站配置请求
 *
 * @Author zjl
 * @Date 2024/1/24 20:53
 */
@Data
public class WebsiteConfigUpdateReqVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名不能为空")
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（0是 1否）
     */
    @NotNull(message = "类型不能为空")
    private Integer configType;

}
