package com.koi.system.mapper.mysql.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.system.domain.oauth2.entity.Oauth2Approve;

import java.util.List;

/**
 * @author 张锦隆
 * @description 针对表【system_oauth2_approve(OAuth2 批准表)】的数据库操作Mapper
 * @createDate 2023-08-01 17:03:08
 * @Entity com.koi.system.oauth2.domain.entity.Oauth2Approve
 */
public interface Oauth2ApproveMapper extends BaseMapper<Oauth2Approve> {

    default int updateOauth2Approve(Oauth2Approve oauth2Approve) {
        return update(oauth2Approve, new LambdaQueryWrapper<Oauth2Approve>()
                .eq(Oauth2Approve::getUserId, oauth2Approve.getUserId())
                .eq(Oauth2Approve::getUserType, oauth2Approve.getUserType())
                .eq(Oauth2Approve::getClientId, oauth2Approve.getClientId())
                .eq(Oauth2Approve::getScope, oauth2Approve.getScope()));
    }

    default List<Oauth2Approve> selectListByUserIdAndUserTypeAndClientId(Long userId, Integer userType, String clientId) {
        return selectList(new LambdaQueryWrapper<Oauth2Approve>()
                .eq(Oauth2Approve::getUserId, userId)
                .eq(Oauth2Approve::getUserType, userType)
                .eq(Oauth2Approve::getClientId, clientId));
    }
}




