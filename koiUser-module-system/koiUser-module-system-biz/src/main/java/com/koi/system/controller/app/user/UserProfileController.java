package com.koi.system.controller.app.user;

import com.koi.common.domain.CommonResult;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.system.convert.user.UserConvert;
import com.koi.system.domain.user.entity.User;
import com.koi.system.domain.user.vo.response.UserInfoRespVO;
import com.koi.system.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * 用户个人中心
 *
 * @Author zjl
 * @Date 2023/8/17 21:12
 */
@RestController
@RequestMapping("/system/user/profile")
@Tag(name = "用户个人中心")
public class UserProfileController {

    @Resource
    private UserService userService;

    /**
     * 获得登录用户信息
     *
     * @param
     * @Return CommonResult<UserInfoRespVO>
     */
    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    public CommonResult<UserInfoRespVO> getUserInfo() {
        User user = userService.getUserById(SecurityFrameworkUtils.getLoginUserId());
        return CommonResult.success(UserConvert.convertUserInfo(user));
    }

    /**
     * 批量获取用户信息
     *
     * @param userIdList
     * @Return CommonResult<List<UserInfoRespVO>>
     */
    @PermitAll
    @GetMapping("/batch")
    @Operation(summary = "批量获取用户信息")
    public CommonResult<List<UserInfoRespVO>> getUserInfoBatch(@RequestParam List<Long> userIdList) {
        List<UserInfoRespVO> userInfoList = userService.getUserInfoList(userIdList);
        return CommonResult.success(userInfoList);
    }

}
