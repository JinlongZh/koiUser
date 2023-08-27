package com.koi.koiAuth.framework.core.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 获得用户基本信息 Response dto
 *
 * @Author zjl
 * @Date 2023/8/27 17:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRespDTO {

    /**
     * 用户id
     */
    private Long id;

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
     * 枚举类 1男 2女 3未知
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 最后登录ip
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

}
