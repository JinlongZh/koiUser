package com.koi.interfacer.service.impl;

import com.koi.interfacer.domain.entity.UserKeyPair;
import com.koi.interfacer.mapper.UserKeyPairMapper;
import com.koi.interfacer.service.UserKeyPairService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户秘钥对 api实现类
 *
 * @Author zjl
 * @Date 2023/8/31 15:05
 */
@Service
public class UserKeyPairServiceImpl implements UserKeyPairService {

    @Resource
    private UserKeyPairMapper userKeyPairMapper;

    @Override
    public UserKeyPair getUserKeyPairByAccessKey(String accessKey) {
        return userKeyPairMapper.selectByAccessKey(accessKey);
    }
}
