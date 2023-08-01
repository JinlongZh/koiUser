//package com.koi.framework.security.core.service;
//
//import cn.hutool.core.collection.CollUtil;
//import com.koi.framework.security.core.LoginUser;
//import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
//
//import java.util.Arrays;
//
///**
// * 描述
// *
// * @Author zjl
// * @Date 2023/8/1 20:26
// */
//public class SecurityFrameworkServiceImpl implements SecurityFrameworkService{
//
//    private final PermissionApi permissionApi;
//
//    @Override
//    public boolean hasPermission(String permission) {
//        return hasAnyPermissions(permission);
//    }
//
//    @Override
//    public boolean hasAnyPermissions(String... permissions) {
//        return permissionApi.hasAnyPermissions(getLoginUserId(), permissions);
//    }
//
//    @Override
//    public boolean hasRole(String role) {
//        return hasAnyRoles(role);
//    }
//
//    @Override
//    public boolean hasAnyRoles(String... roles) {
//        return permissionApi.hasAnyRoles(getLoginUserId(), roles);
//    }
//
//    @Override
//    public boolean hasScope(String scope) {
//        return hasAnyScopes(scope);
//    }
//
//    @Override
//    public boolean hasAnyScopes(String... scope) {
//        LoginUser user = SecurityFrameworkUtils.getLoginUser();
//        if (user == null) {
//            return false;
//        }
//        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
//    }
//
//}
