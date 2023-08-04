package com.koi.system.user.service.impl;

import com.koi.system.user.domain.entity.AdminUser;
import com.koi.system.user.mapper.AdminUserMapper;
import com.koi.system.user.service.AdminUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 后台用户 Service 实现类
 *
 * @Author zjl
 * @Date 2023/8/4 21:31
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public AdminUser getUserByUsername(String username) {
        return adminUserMapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
