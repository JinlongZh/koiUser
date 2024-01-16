package com.koi.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName blog_home_list
 */
@TableName(value = "combined_home_list")
@Data
public class HomeList implements Serializable {

    private Long id;

    private Integer type;

    private String title;

    private Long categoryId;

    private String categoryName;

    private String cover;

    private String images;

    private String content;

    private Integer top;

    private Integer status;

    private Integer viewCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}