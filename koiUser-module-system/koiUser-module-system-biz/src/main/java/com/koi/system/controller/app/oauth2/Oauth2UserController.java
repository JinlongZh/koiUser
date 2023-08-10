package com.koi.system.controller.app.oauth2;

import com.koi.common.domain.CommonResult;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.member.api.user.MemberUserApi;
import com.koi.member.api.user.dto.response.OAuth2UserInfoRespDTO;
import com.koi.system.convert.oauth2.Oauth2UserConvert;
import com.koi.system.domain.oauth2.vo.response.OAuth2UserInfoRespVO;
import com.koi.system.domain.user.entity.User;
import com.koi.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供给外部应用调用为主
 *
 * @Author zjl
 * @Date 2023/8/9 20:16
 */
@Tag(name = "OAuth2.0 用户")
@RestController
@RequestMapping("/system/oauth2/user")
@Slf4j
public class Oauth2UserController {

    @Resource
    private UserService userService;

    /**
     * 获得用户基本信息
     *
     * @param
     * @Return CommonResult<OAuth2UserInfoRespVO>
     */
    @GetMapping("/get")
    @Operation(summary = "获得用户基本信息")
    @PreAuthorize("@ss.hasScope('user.read')")
    public CommonResult<OAuth2UserInfoRespVO> getUserInfo() {
        User user = userService.getUserById(SecurityFrameworkUtils.getLoginUserId());

        return CommonResult.success(Oauth2UserConvert.convertOAuth2UserInfo(user));
    }

}
