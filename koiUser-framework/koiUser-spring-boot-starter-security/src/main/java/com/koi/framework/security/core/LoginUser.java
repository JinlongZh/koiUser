package com.koi.framework.security.core;

import com.koi.common.enums.UserTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 登录用户信息
 *
 * @Author zjl
 * @Date 2023/8/1 20:22
 */
@Accessors(chain = true)
@Data
public class LoginUser {

    /**
     * 用户编号
     */
    private Long id;
    /**
     * 用户类型
     *
     * 关联 {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * 授权范围
     */
    private List<String> scopes;

}
