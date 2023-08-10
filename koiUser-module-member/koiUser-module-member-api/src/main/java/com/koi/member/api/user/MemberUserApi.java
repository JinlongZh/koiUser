package com.koi.member.api.user;

import com.koi.common.domain.CommonResult;
import com.koi.member.api.user.dto.response.OAuth2UserInfoRespDTO;
import com.koi.member.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MemberUser API 接口
 *
 * @Author zjl
 * @Date 2023/8/9 20:29
 */
@FeignClient(name = ApiConstants.NAME)
@Validated
public interface MemberUserApi {

    String PREFIX = ApiConstants.PREFIX + "/user";

    /**
     * 根据 userId 获取用户id
     *
     * @param userId
     * @Return CommonResult<MemberUserRespDTO>
     */
    @GetMapping(PREFIX + "/get")
    CommonResult<OAuth2UserInfoRespDTO> getMemberUser(@RequestParam("userId") Long userId);

}
