package com.koi.interfacer.domain.dto;

import lombok.Data;

/**
 * 接口信息请求参数 dto
 *
 * @Author zjl
 * @Date 2023/9/4 15:34
 */
@Data
public class RequestParamDTO {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 是否必须(0:非必需;  1: 必需)
     */
    private Integer required;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 参数描述
     */
    private String describe;

}
