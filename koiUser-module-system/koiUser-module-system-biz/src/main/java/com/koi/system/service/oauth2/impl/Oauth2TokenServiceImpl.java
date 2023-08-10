package com.koi.system.service.oauth2.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.date.DateUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.system.constants.RedisKeyConstants;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.domain.oauth2.entity.Oauth2RefreshToken;
import com.koi.system.mapper.mysql.oauth2.Oauth2AccessTokenMapper;
import com.koi.system.mapper.mysql.oauth2.Oauth2RefreshTokenMapper;
import com.koi.system.mapper.redis.oauth2.Oauth2AccessTokenRedisDAO;
import com.koi.system.service.oauth2.Oauth2ClientService;
import com.koi.system.service.oauth2.Oauth2TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static com.koi.system.constants.RedisKeyConstants.formatAccessTokenKey;

/**
 * @Author zjl
 * @Date 2023/7/30 16:43
 */
@Service
public class Oauth2TokenServiceImpl implements Oauth2TokenService {

    @Resource
    private Oauth2AccessTokenRedisDAO oauth2AccessTokenRedisDAO;
    @Resource
    private Oauth2ClientService oauth2ClientService;
    @Resource
    private Oauth2AccessTokenMapper oauth2AccessTokenMapper;
    @Resource
    private Oauth2RefreshTokenMapper oauth2RefreshTokenMapper;

    @Override
    public Oauth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        Oauth2Client oauth2Client = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 创建刷新令牌
        Oauth2RefreshToken refreshToken = createOAuth2RefreshToken(userId, userType, oauth2Client, scopes);
        // 创建访问令牌
        return createOAuth2AccessToken(refreshToken, oauth2Client);
    }

    @Override
    public Oauth2AccessToken checkAccessToken(String accessToken) {
        Oauth2AccessToken oauth2AccessToken = this.getAccessToken(accessToken);
        if (oauth2AccessToken == null) {
            throw new ServiceException(UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtils.isExpired(oauth2AccessToken.getExpiresTime())) {
            throw new ServiceException(UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return oauth2AccessToken;
    }

    @Override
    public Oauth2AccessToken getAccessToken(String accessToken) {
        // 优先从 Redis 中获取
        Oauth2AccessToken oauth2AccessToken = oauth2AccessTokenRedisDAO.getAccessToken(accessToken);
        if (oauth2AccessToken != null) {
            return oauth2AccessToken;
        }

        // 获取不到，从 MySQL 中获取
        oauth2AccessToken = oauth2AccessTokenMapper.selectOne(new LambdaQueryWrapper<Oauth2AccessToken>()
                .eq(Oauth2AccessToken::getAccessToken, accessToken));
        // 如果在 MySQL 存在，则往 Redis 中写入
        if (oauth2AccessToken != null && !DateUtils.isExpired(oauth2AccessToken.getExpiresTime())) {
            oauth2AccessTokenRedisDAO.setAccessToken(oauth2AccessToken);
        }
        return oauth2AccessToken;
    }

    @Override
    public Oauth2AccessToken removeAccessToken(String accessToken) {
        // 删除访问令牌
        Oauth2AccessToken oauth2AccessToken = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (oauth2AccessToken == null) {
            return null;
        }
        oauth2AccessTokenMapper.deleteById(oauth2AccessToken.getId());
        // 删除redis中的accessToken
        oauth2AccessTokenRedisDAO.deleteAccessToken(accessToken);
        // 删除刷新令牌
        oauth2RefreshTokenMapper.deleteByRefreshToken(oauth2AccessToken.getRefreshToken());
        return oauth2AccessToken;
    }

    private Oauth2AccessToken createOAuth2AccessToken(Oauth2RefreshToken Oauth2RefreshToken, Oauth2Client oauth2Client) {
        Oauth2AccessToken oauth2AccessToken = Oauth2AccessToken.builder()
                .accessToken(generateAccessToken())
                .userId(Oauth2RefreshToken.getUserId())
                .userType(Oauth2RefreshToken.getUserType())
                .clientId(oauth2Client.getClientId())
                .scopes(Oauth2RefreshToken.getScopes())
                .refreshToken(Oauth2RefreshToken.getRefreshToken())
                .expiresTime(LocalDateTime.now().plusSeconds(oauth2Client.getAccessTokenValiditySeconds()))
                .build();

        oauth2AccessTokenMapper.insert(oauth2AccessToken);
        // accessToken 记录到 Redis 中
        oauth2AccessTokenRedisDAO.setAccessToken(oauth2AccessToken);
        return oauth2AccessToken;
    }

    private Oauth2RefreshToken createOAuth2RefreshToken(Long userId, Integer userType, Oauth2Client oauth2Client, List<String> scopes) {
        Oauth2RefreshToken oauth2RefreshToken = Oauth2RefreshToken.builder()
                .refreshToken(generateRefreshToken())
                .userId(userId)
                .userType(userType)
                .clientId(oauth2Client.getClientId())
                .scopes(JSON.toJSONString(scopes))
                .expiresTime(LocalDateTime.now().plusSeconds(oauth2Client.getRefreshTokenValiditySeconds()))
                .build();
        oauth2RefreshTokenMapper.insert(oauth2RefreshToken);
        return oauth2RefreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }
    
}




