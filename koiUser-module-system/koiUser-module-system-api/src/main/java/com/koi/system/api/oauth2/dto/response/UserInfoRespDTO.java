package com.koi.system.api.oauth2.dto.response;

import com.koi.common.enums.CommonStatusEnum;
import com.koi.system.enums.common.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @Author zjl
 * @Date 2024/8/4
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoRespDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     *
     * 枚举类 {@link SexEnum}
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 帐号状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;

}
