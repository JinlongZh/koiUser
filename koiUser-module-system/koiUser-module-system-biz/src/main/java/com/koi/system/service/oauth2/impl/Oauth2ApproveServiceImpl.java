package com.koi.system.service.oauth2.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.annotations.VisibleForTesting;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.date.DateUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.system.domain.oauth2.entity.Oauth2Approve;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.mapper.mysql.oauth2.Oauth2ApproveMapper;
import com.koi.system.service.oauth2.Oauth2ApproveService;
import com.koi.system.service.oauth2.Oauth2ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 针对表【system_oauth2_approve(OAuth2 批准表)】的数据库操作Service实现
 * @createDate 2023-08-01 17:03:08
 */
@Service
public class Oauth2ApproveServiceImpl extends ServiceImpl<Oauth2ApproveMapper, Oauth2Approve> implements Oauth2ApproveService {

    /**
     * 批准的过期时间，默认 30 天
     */
    private static final Integer TIMEOUT = 30 * 24 * 60 * 60; // 单位：秒

    @Resource
    private Oauth2ClientService oauth2ClientService;
    @Resource
    private Oauth2ApproveMapper oauth2ApproveMapper;

    @Override
    public boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes) {
        // 第一步，基于 Client 的自动授权计算，如果 scopes 都在自动授权中，则返回 true 通过
        Oauth2Client oauth2Client = oauth2ClientService.validOAuthClientFromCache(clientId);
        Assert.notNull(oauth2Client, "客户端不能为空"); // 防御性编程
        if (CollUtil.containsAll(JsonUtils.stringListFromJson(oauth2Client.getAutoApproveScopes()), requestedScopes)) {
            // 如果所有范围都自动批准，则仍需要将批准添加到批准存储中。
            LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
            for (String scope : requestedScopes) {
                saveApprove(userId, userType, clientId, scope, true, expireTime);
            }
            return true;
        }

        // 第二步，算上用户已经批准的授权。如果 scopes 都包含，则返回 true
        List<Oauth2Approve> oauth2ApproveList = getApproveList(userId, userType, clientId);
        Set<String> scopes = CollectionUtils.convertSet(oauth2ApproveList, Oauth2Approve::getScope,
                Oauth2Approve::getApproved); // 只保留未过期的 + 同意的
        return CollUtil.containsAll(scopes, requestedScopes);
    }

    @Override
    public List<Oauth2Approve> getApproveList(Long userId, Integer userType, String clientId) {
        List<Oauth2Approve> oauth2ApproveList = oauth2ApproveMapper.selectListByUserIdAndUserTypeAndClientId(
                userId, userType, clientId);
        // 排除已经过期的
        oauth2ApproveList.removeIf(o -> DateUtils.isExpired(o.getExpiresTime()));
        return oauth2ApproveList;
    }

    @Override
    public boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes) {
        if (CollUtil.isEmpty(requestedScopes)) {
            return true;
        }

        // 更新批准的信息
        boolean success = false; // 需要至少有一个同意
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
        for (Map.Entry<String, Boolean> entry : requestedScopes.entrySet()) {
            if (entry.getValue()) {
                success = true;
            }
            saveApprove(userId, userType, clientId, entry.getKey(), entry.getValue(), expireTime);
        }
        return success;
    }

    @VisibleForTesting
    void saveApprove(Long userId, Integer userType, String clientId,
                     String scope, Boolean approved, LocalDateTime expireTime) {
        // 先更新
        Oauth2Approve oauth2Approve = new Oauth2Approve()
                .setUserId(userId)
                .setUserType(userType)
                .setClientId(clientId)
                .setScope(scope)
                .setApproved(approved)
                .setExpiresTime(expireTime);
        if (oauth2ApproveMapper.updateOauth2Approve(oauth2Approve) == 1) {
            return;
        }
        // 失败，则说明不存在，进行插入
        oauth2ApproveMapper.insert(oauth2Approve);
    }
}




