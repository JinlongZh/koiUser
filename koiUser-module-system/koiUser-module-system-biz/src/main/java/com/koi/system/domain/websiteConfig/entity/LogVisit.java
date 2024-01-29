package com.koi.system.domain.websiteConfig.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName system_log_visit
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value ="system_log_visit")
@Data
public class LogVisit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户id
     */
    private Long userId;

    /**
     * iP
     */
    private String ip;

    /**
     * 归属地
     */
    private String ipSource;

    /**
     * 设备
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后更新时间
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