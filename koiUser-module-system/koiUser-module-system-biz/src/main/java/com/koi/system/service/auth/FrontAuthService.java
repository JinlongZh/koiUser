package com.koi.system.service.auth;

import com.koi.system.domain.auth.vo.request.FrontAuthLoginReqVO;
import com.koi.system.domain.auth.vo.response.AuthLoginRespVO;

import javax.validation.Valid;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/10 14:20
 */
public interface FrontAuthService {

    /**
     * 手机 + 密码登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid FrontAuthLoginReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);

}
