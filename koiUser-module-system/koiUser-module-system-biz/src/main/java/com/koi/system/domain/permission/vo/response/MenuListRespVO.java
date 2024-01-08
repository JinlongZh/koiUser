package com.koi.system.domain.permission.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单返回
 *
 * @Author zjl
 * @Date 2024/1/6 18:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListRespVO {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单icon
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 子菜单
     */
    private List<MenuListRespVO> children;

}
