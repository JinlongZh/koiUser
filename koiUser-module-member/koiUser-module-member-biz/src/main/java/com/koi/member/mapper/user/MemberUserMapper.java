package com.koi.member.mapper.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.member.domain.user.entity.MemberUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户 mapper
 *
 * @author
 * @createDate 2023-08-07 16:37:51
 */
public interface MemberUserMapper extends BaseMapper<MemberUser> {

    default MemberUser selectByMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<MemberUser>()
                .eq(MemberUser::getMobile, mobile));
    }
}




