package com.koi.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName blog_article
 */
@TableName(value ="blog_article")
@Data
public class Article implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 是否置顶 0否 1是
     */
    private Integer articleTop;

    /**
     * 状态值 0公开 1关闭
     */
    private Integer status;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 发表时间
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