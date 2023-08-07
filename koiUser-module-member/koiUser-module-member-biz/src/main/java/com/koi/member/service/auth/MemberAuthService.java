package com.koi.member.service.auth;

import com.koi.member.domain.auth.vo.request.AppAuthLoginReqVO;
import com.koi.member.domain.auth.vo.response.AppAuthLoginRespVO;

import javax.validation.Valid;

/**
 * 用户的认证 Service 接口
 *
 * @Author zjl
 * @Date 2023/8/6 20:49
 */
public interface MemberAuthService {

    /**
     * 手机 + 密码登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppAuthLoginRespVO login(@Valid AppAuthLoginReqVO reqVO);
}
