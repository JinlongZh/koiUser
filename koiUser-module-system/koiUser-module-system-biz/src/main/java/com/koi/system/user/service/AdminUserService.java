package com.koi.system.user.service;

import com.koi.system.user.domain.entity.AdminUser;

/**
 * 后台用户 Service 接口
 *
 * @Author zjl
 * @Date 2023/8/4 21:30
 */
public interface AdminUserService {

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    AdminUser getUserByUsername(String username);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
