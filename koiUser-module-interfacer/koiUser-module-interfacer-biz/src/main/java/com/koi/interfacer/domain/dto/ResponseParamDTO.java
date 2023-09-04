package com.koi.interfacer.domain.dto;

import lombok.Data;

/**
 * 接口信息响应参数 dto
 *
 * @Author zjl
 * @Date 2023/9/4 15:33
 */
@Data
public class ResponseParamDTO {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 参数描述
     */
    private String describe;

}
