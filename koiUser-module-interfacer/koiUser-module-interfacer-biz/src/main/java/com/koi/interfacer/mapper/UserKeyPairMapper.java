package com.koi.interfacer.mapper;

import com.koi.framework.mybatis.core.mapper.BaseMapperX;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.interfacer.domain.entity.UserKeyPair;

/**
 * 用户密钥对 mapper
 *
 * @Author zjl
 * @Date 2023/8/29 14:46:51
 */
public interface UserKeyPairMapper extends BaseMapperX<UserKeyPair> {

    default UserKeyPair selectByAccessKey(String accessKey) {
        return selectOne(new LambdaQueryWrapperX<UserKeyPair>()
                .eq(UserKeyPair::getAccessKey, accessKey));
    }
}




