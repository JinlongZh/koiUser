package com.koi.member.service.user;

import com.koi.member.domain.user.entity.MemberUser;

/**
 * 用户 Service 接口
 *
 * @Author zjl
 * @Date 2023/8/7 16:48
 */
public interface MemberUserService {

    /**
     * 通过手机查询用户
     *
     * @param mobile 手机
     * @return 用户对象
     */
    MemberUser getUserByMobile(String mobile);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
