package com.koi.system.permission.service.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.system.permission.domain.entity.UserRole;
import com.koi.system.permission.mapper.UserRoleMapper;
import com.koi.system.permission.service.PermissionService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限 Service 实现类
 *
 * @Author zjl
 * @Date 2023/8/3 9:59
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private UserRoleMapper userRoleMapper;
    

    /**
     * 用户编号与角色编号的缓存映射
     * key：用户编号
     * value：角色编号的数组
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    @Setter // 单元测试需要
    private volatile Map<Long, Set<Long>> userRoleCache;

    @Override
    @PostConstruct
    public void initLocalCache() {
        initLocalCacheForUserRole();
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        return false;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        return false;
    }

    /**
     * 刷新 userRole 缓存
     */
    @VisibleForTesting
    void initLocalCacheForUserRole() {
        // 加载数据
        List<UserRole> userRoles = userRoleMapper.selectList(null);
        log.info("[initLocalCacheForUserRole][缓存用户与角色，数量为:{}]", userRoles.size());

        // 构建缓存
        ImmutableMultimap.Builder<Long, Long> userRoleCacheBuilder = ImmutableMultimap.builder();
        userRoles.forEach(userRoleDO -> userRoleCacheBuilder.put(userRoleDO.getUserId(), userRoleDO.getRoleId()));
        userRoleCache = CollectionUtils.convertMultiMap2(userRoles, UserRole::getUserId, UserRole::getRoleId);
    }
}
