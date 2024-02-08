package com.koi.system.domain.permission.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/2/8 17:30
 */
@Data
public class MenuStatusReqVO {

    @NotNull(message = "菜单ID不能为空")
    private Long id;

    @NotNull(message = "状态不能为空")
    private Integer status;

}
