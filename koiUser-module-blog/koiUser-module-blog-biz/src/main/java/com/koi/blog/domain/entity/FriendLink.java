package com.koi.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName blog_friend_link
 */
@TableName(value ="blog_friend_link")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLink implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 友链头像
     */
    private String linkAvatar;

    /**
     * 友链地址
     */
    private String linkAddress;

    /**
     * 友链介绍
     */
    private String linkIntro;

    /**
     * 友链状态 0公开 1关闭 2审核中 3拒绝
     */
    private Integer status;

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