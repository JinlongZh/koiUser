package com.koi.system.oauth2.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * OAuth2 客户端表
 * @TableName system_oauth2_client
 */
@TableName(value ="system_oauth2_client")
@Data
public class Oauth2Client implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户端编号
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String secret;

    /**
     * 应用名
     */
    private String name;

    /**
     * 应用图标
     */
    private String logo;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 访问令牌的有效期
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新令牌的有效期
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 可重定向的 URI 地址
     * -----list类型：["aaa","bbb"]
     */
    private String redirectUris;

    /**
     * 授权类型
     * -----list类型：["aaa","bbb"]
     */
    private String authorizedGrantTypes;

    /**
     * 授权范围
     * -----list类型：["aaa","bbb"]
     */
    private String scopes;

    /**
     * 自动通过的授权范围
     * code 授权时，如果 scope 在这个范围内，则自动通过
     * -----list类型：["aaa","bbb"]
     */
    private String autoApproveScopes;

    /**
     * 权限
     * -----list类型：["aaa","bbb"]
     */
    private String authorities;

    /**
     * 资源
     * -----list类型：["aaa","bbb"]
     */
    private String resourceIds;

    /**
     * 附加信息
     * JSON 格式
     */
    private String additionalInformation;

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