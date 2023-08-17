package com.koi.system.domain.user.vo.response;

import com.koi.system.enums.common.SexEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户个人中心信息 Response VO
 *
 * @Author zjl
 * @Date 2023/8/17 21:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户个人中心信息 Response VO")
public class UserInfoRespVO {

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
     * 枚举类 {@link SexEnum}
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
