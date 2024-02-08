package com.koi.system.controller.admin.permission;

import com.koi.common.domain.CommonResult;
import com.koi.system.domain.permission.vo.request.MenuAddReqVO;
import com.koi.system.domain.permission.vo.request.MenuQueryReqVO;
import com.koi.system.domain.permission.vo.request.MenuStatusReqVO;
import com.koi.system.domain.permission.vo.request.MenuUpdateReqVO;
import com.koi.system.domain.permission.vo.response.MenuListRespVO;
import com.koi.system.domain.permission.vo.response.MenuRespVO;
import com.koi.system.service.permission.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
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

    @PermitAll
    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    public CommonResult<List<MenuRespVO>> listMenus(MenuQueryReqVO req) {
        return CommonResult.success(menuService.listMenus(req));
    }

    @PermitAll
    @PostMapping("/update")
    @Operation(summary = "更新菜单")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO req) {
        menuService.updateMenu(req);
        return CommonResult.success(true);
    }

    @PermitAll
    @PostMapping("/add")
    @Operation(summary = "创建菜单")
    public CommonResult<Boolean> createMenu(@Valid @RequestBody MenuAddReqVO req) {
        menuService.addMenu(req);
        return CommonResult.success(true);
    }

    @PermitAll
    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return CommonResult.success(true);
    }

    @PermitAll
    @PutMapping("/status")
    @Operation(summary = "更新菜单状态")
    public CommonResult<Boolean> updateMenuStatus(@Valid @RequestBody MenuStatusReqVO req) {
        menuService.updateMenuStatus(req);
        return CommonResult.success(true);
    }

}
