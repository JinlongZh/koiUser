package com.koi.system.domain.permission.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 后台菜单更新 req-vo
 *
 * @Author zjl
 * @Date 2024/2/8 17:14
 */
@Data
public class MenuUpdateReqVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    private String name;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    private String permission;

    /**
     * 菜单类型
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer orderNum;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 菜单路径
     */
    @NotBlank(message = "菜单路径不能为空")
    private String path;

    /**
     * 组件
     */
    @NotBlank(message = "组件不能为空")
    private String component;

    /**
     * 菜单icon
     */
    @NotBlank(message = "菜单icon不能为空")
    private String icon;

}
