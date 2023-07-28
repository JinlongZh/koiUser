package com.koi.koiuserserver.service.Impl;

import com.koi.common.utils.JwtUtils;
import com.koi.framework.redis.core.constant.RedisKey;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.koiuserserver.domain.vo.request.UserReq;
import com.koi.koiuserserver.domain.vo.response.TokenResp;
import com.koi.koiuserserver.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/28 11:42
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private JwtUtils jwtUtils;

    //token过期时间
    private static final Integer TOKEN_EXPIRE_DAYS = 5;
    //token续期时间
    private static final Integer TOKEN_RENEWAL_DAYS = 2;

    @Override
    public TokenResp login(UserReq userReq) {
        // 登录逻辑

        // 获取用户token
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, 1L);
        String accessToken = jwtUtils.createToken(1L);
        // token过期用redis中心化控制，初期采用5天过期，剩1天自动续期的方案。后续可以用双token实现
        RedisUtils.set(key, accessToken, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);

        return TokenResp.builder()
                .accessToken(accessToken)
                .build();
    }
}
