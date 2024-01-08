package com.koi.system.controller.admin.permission;

import com.koi.common.domain.CommonResult;
import com.koi.system.domain.permission.vo.response.MenuListRespVO;
import com.koi.system.service.permission.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/6 17:24
 */
@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PermitAll
    @GetMapping("/user")
    @Operation(summary = "获取用户菜单")
    public CommonResult<List<MenuListRespVO>> listUserMenus() {
        return CommonResult.success(menuService.listUserMenus());
    }

}
