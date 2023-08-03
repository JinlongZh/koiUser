package com.koi.system.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Sets;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.system.permission.domain.entity.Role;
import com.koi.system.permission.domain.entity.UserRole;
import com.koi.system.permission.mapper.UserRoleMapper;
import com.koi.system.permission.service.PermissionService;
import com.koi.system.permission.service.RoleService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static com.koi.common.utils.collection.CollectionUtils.convertSet;
import static java.util.Collections.singleton;

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
    @Resource
    private RoleService roleService;
    

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
    public Set<Long> getUserRoleIdsFromCache(Long userId, Collection<Integer> roleStatuses) {
        Set<Long> cacheRoleIds = userRoleCache.get(userId);
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtil.isEmpty(cacheRoleIds)) {
            return Collections.emptySet();
        }
        Set<Long> roleIds = new HashSet<>(cacheRoleIds);
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                Role role = roleService.getRoleFromCache(roleId);
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // TODO 判断菜单权限
        return true;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIdsFromCache(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(roleService.getRoleListFromCache(roleIds),
                Role::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
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
