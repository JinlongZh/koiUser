package com.koi.member.api.user;

import com.koi.common.domain.CommonResult;
import com.koi.member.api.user.dto.response.OAuth2UserInfoRespDTO;
import com.koi.member.convert.user.MemberUserConvert;
import com.koi.member.service.user.MemberUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供 RESTful API 接口，给 Feign 调用
 *
 * @Author zjl
 * @Date 2023/8/9 20:39
 */
@RestController
public class MemberUserApiImpl implements MemberUserApi {

    @Resource
    private MemberUserService memberUserService;

    @Override
    public CommonResult<OAuth2UserInfoRespDTO> getMemberUser(Long userId) {
        return CommonResult.success(MemberUserConvert.convertOAuth2UserInfo(memberUserService.getUser(userId)));
    }
}
