package com.koi.system.oauth2.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Builder;
import lombok.Data;

/**
 * OAuth2 授权码表
 *
 * @TableName system_oauth2_code
 */
@Builder
@TableName(value = "system_oauth2_code")
@Data
public class Oauth2Code implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 授权码
     */
    private String code;

    /**
     * 客户端编号
     */
    private String clientId;

    /**
     * 授权范围
     * -----list类型：["aaa","bbb"]
     */
    private String scopes;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

    /**
     * 可重定向的 URI 地址
     */
    private String redirectUri;

    /**
     * 状态
     */
    private String state;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}