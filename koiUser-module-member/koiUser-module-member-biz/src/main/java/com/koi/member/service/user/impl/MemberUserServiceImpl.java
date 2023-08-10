package com.koi.member.service.user.impl;

import com.koi.member.domain.user.entity.MemberUser;
import com.koi.member.mapper.user.MemberUserMapper;
import com.koi.member.service.user.MemberUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 实现类
 *
 * @Author zjl
 * @Date 2023/8/7 16:48
 */
@Service
public class MemberUserServiceImpl implements MemberUserService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public MemberUser getUserByMobile(String mobile) {
        return memberUserMapper.selectByMobile(mobile);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public MemberUser getUser(Long id) {
        return memberUserMapper.selectById(id);
    }
}
