package com.koi.system.service.permission.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.system.domain.permission.entity.Role;
import com.koi.system.enums.permission.RoleCodeEnum;
import com.koi.system.mapper.mysql.permission.RoleMapper;
import com.koi.system.service.permission.RoleService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description 针对表【system_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-08-03 10:14:27
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 角色缓存
     * key：角色编号 {@link Role#getId()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, Role> roleCache;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<Role> roleList = roleMapper.selectList(null);
        log.info("[initLocalCache][缓存角色，数量为:{}]", roleList.size());

        // 第二步：构建缓存
        roleCache = CollectionUtils.convertMap(roleList, Role::getId);
    }

    @Override
    public Role getRoleFromCache(Long id) {
        return roleCache.get(id);
    }

    @Override
    public List<Role> getRoleListFromCache(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleCache.values().stream().filter(role -> ids.contains(role.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<Role> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }
}




