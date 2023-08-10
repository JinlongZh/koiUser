package com.koi.system.service.user.impl;

import com.koi.system.domain.user.entity.User;
import com.koi.system.mapper.mysql.user.UserMapper;
import com.koi.system.service.user.UserService;
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
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userMapper.selectBuMobile(mobile);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
}
