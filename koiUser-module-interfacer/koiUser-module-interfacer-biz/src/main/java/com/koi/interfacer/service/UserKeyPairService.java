package com.koi.interfacer.service;

import com.koi.interfacer.domain.entity.UserKeyPair;

/**
 * 用户秘钥对 api
 *
 * @Author zjl
 * @Date 2023/8/31 15:05
 */
public interface UserKeyPairService {

    /**
     * 根据accessKey获取用户秘钥对
     *
     * @param accessKey
     * @Return UserKeyPair
     */
    UserKeyPair getUserKeyPairByAccessKey(String accessKey);

}
