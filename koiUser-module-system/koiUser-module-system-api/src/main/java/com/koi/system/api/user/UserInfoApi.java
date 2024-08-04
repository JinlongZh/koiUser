package com.koi.system.api.user;

import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;

import java.util.Map;
import java.util.Set;

/**
 * 用户信息 Api
 *
 * @Author zjl
 * @Date 2024/8/4
 */
public interface UserInfoApi {

    UserInfoRespDTO getUserInfoByUserId(Long id);

    Map<Long, UserInfoRespDTO> getUserInfoByUserIds(Set<Long> ids);
}
