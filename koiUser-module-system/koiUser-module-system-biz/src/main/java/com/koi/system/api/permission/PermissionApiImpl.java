package com.koi.system.api.permission;

import com.koi.system.service.permission.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限 API 实现类
 *
 * @Author zjl
 * @Date 2023/8/3 11:06
 */
@Service
public class PermissionApiImpl implements PermissionApi{

    @Resource
    private PermissionService permissionService;

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        return permissionService.hasAnyPermissions(userId, permissions);
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        return permissionService.hasAnyRoles(userId, roles);
    }
}
