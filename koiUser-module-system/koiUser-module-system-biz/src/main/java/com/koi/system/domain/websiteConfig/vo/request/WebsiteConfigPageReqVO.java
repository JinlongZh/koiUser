package com.koi.system.domain.websiteConfig.vo.request;

import com.koi.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/24 21:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebsiteConfigPageReqVO extends PageParam {

    /**
     * 参数键名
     */
    private String configName;

    /**
     * 系统内置（0是 1否）
     */
    private Integer configType;

}
