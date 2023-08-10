package com.koi.system.mapper.mysql.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.system.domain.oauth2.entity.Oauth2RefreshToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author zjl
 * @Date 2023/7/30 19:43
 */
public interface Oauth2RefreshTokenMapper extends BaseMapper<Oauth2RefreshToken> {

    default void deleteByRefreshToken(String refreshToken) {
        delete(new LambdaQueryWrapper<Oauth2RefreshToken>()
                .eq(Oauth2RefreshToken::getRefreshToken, refreshToken));
    }
}




