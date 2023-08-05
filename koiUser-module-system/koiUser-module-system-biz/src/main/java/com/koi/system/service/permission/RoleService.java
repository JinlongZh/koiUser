package com.koi.system.service.permission;


import com.baomidou.mybatisplus.extension.service.IService;
import com.koi.system.domain.permission.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @description 针对表【system_role(角色信息表)】的数据库操作Service
 * @createDate 2023-08-03 10:14:27
 */
public interface RoleService extends IService<Role> {

    /**
     * 初始化角色的本地缓存
     */
    void initLocalCache();

    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getRoleFromCache(Long id);

    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<Role> getRoleListFromCache(Collection<Long> ids);

    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<Role> roleList);

    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnySuperAdmin(Set<Long> ids) {
        return hasAnySuperAdmin(getRoleListFromCache(ids));
    }
}
