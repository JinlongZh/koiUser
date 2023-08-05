package com.koi.system.service.auth;

import com.koi.system.domain.auth.vo.request.AuthLoginReqVO;
import com.koi.system.domain.auth.vo.response.AuthLoginRespVO;
import com.koi.system.domain.user.entity.AdminUser;

import javax.validation.Valid;

/**
 * @Author zjl
 * @Date 2023/8/4 21:12
 */
public interface AuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    AdminUser authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);
}
