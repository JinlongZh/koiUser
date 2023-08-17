package com.koi.system.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.exception.ServiceException;
import com.koi.system.convert.auth.AuthConvert;
import com.koi.system.domain.auth.vo.request.FrontAuthLoginReqVO;
import com.koi.system.domain.auth.vo.response.AuthLoginRespVO;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.user.entity.User;
import com.koi.system.enums.oauth2.OAuth2ClientConstants;
import com.koi.system.service.auth.FrontAuthService;
import com.koi.system.service.oauth2.Oauth2TokenService;
import com.koi.system.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/10 14:21
 */
@Service
public class FrontAuthServiceImpl implements FrontAuthService {

    @Resource
    private UserService userService;
    @Resource
    private Oauth2TokenService oauth2TokenService;


    @Override
    public AuthLoginRespVO login(FrontAuthLoginReqVO reqVO) {
        // 使用手机 + 密码，进行登录。
        // 校验账号是否存在
        User user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        if (!userService.isPasswordMatch(reqVO.getPassword(), user.getPassword())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号被禁用");
        }
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId());
    }

    @Override
    public void logout(String token) {
        oauth2TokenService.removeAccessToken(token);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.convertAuthLogin(oauth2AccessToken);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId) {
        // 创建访问令牌
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.convertAuthLogin(oauth2AccessToken);
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
