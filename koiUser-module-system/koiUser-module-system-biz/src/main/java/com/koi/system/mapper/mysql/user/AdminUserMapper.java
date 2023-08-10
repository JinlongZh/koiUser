package com.koi.system.mapper.mysql.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.system.domain.user.entity.AdminUser;

/**
 * @Author zjl
 * @Date 2023/8/4 21:32
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {
    default AdminUser selectByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
    }
}
