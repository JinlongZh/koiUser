package com.koi.system.mapper.mysql.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.system.domain.user.entity.User;

/**
 * @Author zjl
 * @Date 2023/8/4 21:32
 */
public interface UserMapper extends BaseMapper<User> {
    default User selectByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
    }

    default User selectBuMobile(String mobile) {
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getMobile, mobile));
    }
}
