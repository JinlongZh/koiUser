package com.koi.member.controller.app.auth;

import com.koi.common.domain.CommonResult;
import com.koi.member.domain.auth.vo.request.AppAuthLoginReqVO;
import com.koi.member.domain.auth.vo.response.AppAuthLoginRespVO;
import com.koi.member.service.auth.MemberAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 *
 *
 * @Author zjl
 * @Date 2023/8/5 16:15
 */
@Tag(name = "用户 APP - 认证")
@RestController
@RequestMapping("/member/auth")
public class AppAuthController {

    @Resource
    private MemberAuthService authService;

    @PostMapping("/login")
    @Operation(summary = "使用手机 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return CommonResult.success(authService.login(reqVO));
    }

}
