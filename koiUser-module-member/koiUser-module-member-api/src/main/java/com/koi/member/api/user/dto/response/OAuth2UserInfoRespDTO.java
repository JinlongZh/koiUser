package com.koi.member.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * OAuth2 用户基本信息
 *
 * @Author zjl
 * @Date 2023/8/9 20:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfoRespDTO implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户头像
     */
    private String avatar;

}
