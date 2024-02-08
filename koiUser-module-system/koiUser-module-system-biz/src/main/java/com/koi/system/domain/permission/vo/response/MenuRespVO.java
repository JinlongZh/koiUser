package com.koi.system.domain.permission.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台菜单管理 resp-vo
 *
 * @Author zjl
 * @Date 2024/2/7 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRespVO {

    /**
     * 主键
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
     * 是否隐藏  0否1是
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 子菜单
     */
    private List<MenuRespVO> children;

}
