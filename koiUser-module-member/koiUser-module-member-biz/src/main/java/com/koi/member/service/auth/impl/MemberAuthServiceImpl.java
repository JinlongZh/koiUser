package com.koi.member.service.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.exception.ServiceException;
import com.koi.member.convert.auth.AuthConvert;
import com.koi.member.domain.auth.vo.request.AppAuthLoginReqVO;
import com.koi.member.domain.auth.vo.response.AppAuthLoginRespVO;
import com.koi.member.domain.user.entity.MemberUser;
import com.koi.member.service.auth.MemberAuthService;
import com.koi.member.service.user.MemberUserService;
import com.koi.system.api.oauth2.OAuth2TokenApi;
import com.koi.system.api.oauth2.dto.request.OAuth2AccessTokenCreateReqDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;
import com.koi.system.enums.oauth2.OAuth2ClientConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * 用户的认证 Service 实现类
 *
 * @Author zjl
 * @Date 2023/8/6 20:49
 */
@Service
public class MemberAuthServiceImpl implements MemberAuthService {

    @Resource
    private MemberUserService userService;

    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Override
    public AppAuthLoginRespVO login(AppAuthLoginReqVO reqVO) {
        // 使用手机 + 密码，进行登录。
        // 校验账号是否存在
        MemberUser user = userService.getUserByMobile(reqVO.getMobile());
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
        return createTokenAfterLoginSuccess(user);
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        oauth2TokenApi.removeAccessToken(token);
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(MemberUser user) {

        // 创建 Token 令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
                .setUserId(user.getId())
                .setUserType(getUserType().getValue())
                .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));
        // 构建返回结果
        return AuthConvert.convertAppAuthLogin(accessTokenRespDTO);
    }


    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
