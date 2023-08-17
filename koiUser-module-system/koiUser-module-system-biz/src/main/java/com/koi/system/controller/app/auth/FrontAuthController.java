package com.koi.system.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import com.koi.common.domain.CommonResult;
import com.koi.framework.security.config.SecurityProperties;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.system.domain.auth.vo.request.FrontAuthLoginReqVO;
import com.koi.system.domain.auth.vo.response.AuthLoginRespVO;
import com.koi.system.service.auth.FrontAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/10 14:22
 */
@Slf4j
@Tag(name = "用户前台  - 认证")
@RestController
@RequestMapping("/system/auth")
public class FrontAuthController {

    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private FrontAuthService frontAuthService;


    /**
     * 使用手机 + 密码登录
     *
     * @param reqVO
     * @Return CommonResult<AuthLoginResp>
     */
    @PermitAll
    @PostMapping("/login")
    @Operation(summary = "使用手机 + 密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid FrontAuthLoginReqVO reqVO) {
        return CommonResult.success(frontAuthService.login(reqVO));
    }

    /**
     * 退出登录
     *
     * @param request
     * @Return CommonResult<Boolean>
     */
    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "退出登录")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            frontAuthService.logout(token);
        }
        return CommonResult.success(true);
    }

    /**
     * 刷新令牌
     *
     * @param refreshToken
     * @Return CommonResult<AuthLoginRespVO>
     */
    @PermitAll
    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return CommonResult.success(frontAuthService.refreshToken(refreshToken));
    }


}
