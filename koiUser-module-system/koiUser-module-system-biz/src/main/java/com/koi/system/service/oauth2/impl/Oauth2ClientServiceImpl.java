package com.koi.system.service.oauth2.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.common.utils.string.StrUtils;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.mapper.mysql.oauth2.Oauth2ClientMapper;
import com.koi.system.service.oauth2.Oauth2ClientService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * OAuth2.0 Client Service 实现类
 *
 * @Author zjl
 * @Date 2023/7/30 16:43
 */

@Slf4j
@Service
public class Oauth2ClientServiceImpl implements Oauth2ClientService {

    @Resource
    private Oauth2ClientMapper oauth2ClientMapper;

    /**
     * 客户端缓存, key：客户端编号, volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter // 解决单测
    @Setter // 解决单测
    private volatile Map<String, Oauth2Client> clientCache;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<Oauth2Client> clients = oauth2ClientMapper.selectList(null);
        log.info("[initLocalCache][缓存 OAuth2 客户端，数量为:{}]", clients.size());

        // 第二步：构建缓存。
        clientCache = CollectionUtils.convertMap(clients, Oauth2Client::getClientId);
    }

    @Override
    public Oauth2Client validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType, Collection<String> scopes, String redirectUri) {
        // 校验客户端存在、且开启
        Oauth2Client oauth2Client = clientCache.get(clientId);
        if (oauth2Client == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "OAuth2 客户端不存在");
        }
        if (ObjectUtil.notEqual(oauth2Client.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw new ServiceException(BAD_REQUEST.getCode(), "OAuth2 客户端已禁用");
        }

        // 校验客户端密钥
        if (StrUtil.isNotEmpty(clientSecret) && ObjectUtil.notEqual(oauth2Client.getSecret(), clientSecret)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "无效 client_secret");
        }
        // 校验授权方式
        if (StrUtil.isNotEmpty(authorizedGrantType) && !CollUtil.contains(JSON.parseObject(oauth2Client.getAuthorizedGrantTypes(), List.class), authorizedGrantType)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "不支持该授权类型");
        }
        // 校验授权范围
        if (CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(JSON.parseObject(oauth2Client.getScopes(), List.class), scopes)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "授权范围过大");
        }
        // 校验回调地址
        if (StrUtil.isNotEmpty(redirectUri) && !StrUtils.startWithAny(redirectUri, JsonUtils.stringListFromJson(oauth2Client.getRedirectUris()))) {
            throw new ServiceException(BAD_REQUEST.getCode(), "无效 redirect_uri");
        }
        return oauth2Client;
    }
}
