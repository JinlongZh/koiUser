package com.koi.system.permission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koi.system.permission.domain.entity.Role;
import com.koi.system.permission.mapper.RoleMapper;
import com.koi.system.permission.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @description 针对表【system_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-08-03 10:14:27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}




