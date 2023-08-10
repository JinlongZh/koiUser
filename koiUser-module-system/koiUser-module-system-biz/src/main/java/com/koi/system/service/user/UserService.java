package com.koi.system.service.user;

import com.koi.system.domain.user.entity.User;

/**
 * 后台用户 Service 接口
 *
 * @Author zjl
 * @Date 2023/8/4 21:30
 */
public interface UserService {

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    User getUserByUsername(String username);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

    /**
     * 通过手机查询用户
     *
     * @param mobile 手机
     * @return 用户对象
     */
    User getUserByMobile(String mobile);

    /**
     * 通过id查询用户
     *
     * @param userId 用户id
     * @Return User 用户对象
     */
    User getUserById(Long userId);
}
