package com.koi.koiuserserver.service;

import com.koi.koiuserserver.domain.vo.request.UserReq;
import com.koi.koiuserserver.domain.vo.response.TokenResp;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/28 11:41
 */
public interface AuthService {
    /**
     * 登录
     *
     * @param userReq
     * @Return TokenResp
     * @Date 2023/7/28 11:47
     */
    TokenResp login(UserReq userReq);
}
