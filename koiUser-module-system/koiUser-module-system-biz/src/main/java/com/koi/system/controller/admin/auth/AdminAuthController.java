package com.koi.system.controller.admin.auth;

import com.koi.common.domain.CommonResult;
import com.koi.system.domain.auth.vo.request.AuthLoginReq;
import com.koi.system.domain.auth.vo.response.AuthLoginResp;
import com.koi.system.service.auth.AdminAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * @Author zjl
 * @Date 2023/8/4 21:08
 */
@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Slf4j
public class AdminAuthController {

    @Resource
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号密码登录")
    public CommonResult<AuthLoginResp> login(@RequestBody @Valid AuthLoginReq reqVO) {
        return CommonResult.success(adminAuthService.login(reqVO));
    }

}
