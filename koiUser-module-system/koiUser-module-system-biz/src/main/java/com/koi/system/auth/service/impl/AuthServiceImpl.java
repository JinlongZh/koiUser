package com.koi.system.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.exception.ServiceException;
import com.koi.system.auth.convert.AuthConvert;
import com.koi.system.auth.domain.vo.request.AuthLoginReqVO;
import com.koi.system.auth.domain.vo.response.AuthLoginRespVO;
import com.koi.system.auth.service.AuthService;
import com.koi.system.common.enums.LoginLogTypeEnum;
import com.koi.system.oauth2.domain.entity.Oauth2AccessToken;
import com.koi.system.oauth2.enums.OAuth2ClientConstants;
import com.koi.system.oauth2.service.Oauth2TokenService;
import com.koi.system.user.domain.entity.AdminUser;
import com.koi.system.user.service.AdminUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/4 21:14
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthConvert authConvert;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private Oauth2TokenService oauth2TokenService;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${koiuser.captcha.enable:true}")
    private Boolean captchaEnable;


    @Override
    public AdminUser authenticate(String username, String password) {
        // 校验账号是否存在
        AdminUser user = adminUserService.getUserByUsername(username);
        if (user == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        if (!adminUserService.isPasswordMatch(password, user.getPassword())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号密码不正确");
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "登录失败，账号已禁用");
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 使用账号密码，进行登录
        AdminUser user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 创建访问令牌
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return authConvert.convertAuthLogin(oauth2AccessToken);
    }


    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }


}
