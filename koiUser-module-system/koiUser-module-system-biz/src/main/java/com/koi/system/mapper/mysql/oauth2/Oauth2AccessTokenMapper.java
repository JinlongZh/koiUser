package com.koi.system.mapper.mysql.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/30 17:43
 */
public interface Oauth2AccessTokenMapper extends BaseMapper<Oauth2AccessToken> {

    default Oauth2AccessToken selectByAccessToken(String accessToken) {
        return selectOne(new LambdaQueryWrapper<Oauth2AccessToken>()
                .eq(Oauth2AccessToken::getAccessToken, accessToken));
    }

    default List<Oauth2AccessToken> selectListByRefreshToken(String refreshToken) {
        return selectList(new LambdaQueryWrapper<Oauth2AccessToken>()
                .eq(Oauth2AccessToken::getRefreshToken, refreshToken));
    }
}




