package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台文章resp-vo
 *
 * @Author zjl
 * @Date 2024/1/10 17:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAdminRespVO {

    /**
     * id
     */
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
     * 分类id
     */
    private String categoryName;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
