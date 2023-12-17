package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章 resp-vo
 *
 * @Author zjl
 * @Date 2023/12/17 11:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRespVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 是否置顶
     */
    private Integer articleTop;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
