package com.koi.system.api.permission;

import com.koi.common.domain.CommonResult;
import com.koi.system.service.permission.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供 RESTful API 接口，给 Feign 调用
 *
 * @Author zjl
 * @Date 2023/8/3 11:06
 */
@RestController
@Service
public class PermissionApiImpl implements PermissionApi{

    @Resource
    private PermissionService permissionService;

    @Override
    public CommonResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return CommonResult.success(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public CommonResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return CommonResult.success(permissionService.hasAnyRoles(userId, roles));
    }
}
