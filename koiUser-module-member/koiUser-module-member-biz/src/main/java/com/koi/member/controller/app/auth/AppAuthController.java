package com.koi.member.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import com.koi.common.domain.CommonResult;
import com.koi.framework.security.config.SecurityProperties;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.member.domain.auth.vo.request.AppAuthLoginReqVO;
import com.koi.member.domain.auth.vo.response.AppAuthLoginRespVO;
import com.koi.member.service.auth.MemberAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *
 *
 * @Author zjl
 * @Date 2023/8/5 16:15
 */
@Slf4j
@Tag(name = "用户 APP - 认证")
@RestController
@RequestMapping("/member/auth")
public class AppAuthController {

    @Resource
    private MemberAuthService authService;

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 使用手机 + 密码登录
     *
     * @param reqVO
     * @Return CommonResult<AppAuthLoginRespVO>
     */
    @PermitAll
    @PostMapping("/login")
    @Operation(summary = "使用手机 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return CommonResult.success(authService.login(reqVO));
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
            authService.logout(token);
        }
        return CommonResult.success(true);
    }

}
