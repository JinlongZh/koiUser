package com.koi.system.api.permission;

import com.koi.common.domain.CommonResult;
import com.koi.system.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 权限 Api
 *
 * @Author zjl
 * @Date 2023/8/3 10:59
 */
@FeignClient(name = ApiConstants.NAME)
public interface PermissionApi {

    String PREFIX = ApiConstants.PREFIX + "/permission";

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId 用户编号
     * @param permissions 权限
     * @return 是否
     */
    @GetMapping(PREFIX + "/has-any-permissions")
    CommonResult<Boolean> hasAnyPermissions(@RequestParam("userId") Long userId,
                                            @RequestParam("permissions") String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param userId 用户编号
     * @param roles 角色数组
     * @return 是否
     */
    @GetMapping(PREFIX + "/has-any-roles")
    CommonResult<Boolean> hasAnyRoles(@RequestParam("userId") Long userId,
                                      @RequestParam("roles") String... roles);

}
