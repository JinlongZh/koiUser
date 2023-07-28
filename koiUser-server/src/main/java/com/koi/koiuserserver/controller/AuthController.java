package com.koi.koiuserserver.controller;

import com.koi.common.result.Result;
import com.koi.koiuserserver.domain.dto.LoginUser;
import com.koi.koiuserserver.domain.vo.request.UserReq;
import com.koi.koiuserserver.domain.vo.response.TokenResp;
import com.koi.koiuserserver.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.koi.koiuserserver.utils.SecurityUtils.getLoginUser;

/**
 * 权限控制器
 *
 * @Author zjl
 * @Date 2023/7/27 15:59
 */
@RestController
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result<TokenResp> login(@Valid @RequestBody UserReq userReq) {
        TokenResp tokenResp = authService.login(userReq);
        return Result.ok(tokenResp);
    }

}
