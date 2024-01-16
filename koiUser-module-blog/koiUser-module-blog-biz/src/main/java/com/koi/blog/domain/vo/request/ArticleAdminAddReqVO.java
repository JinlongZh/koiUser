package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 后台文章添加req-vo
 *
 * @Author zjl
 * @Date 2024/1/10 18:30
 */
@Data
public class ArticleAdminAddReqVO {

    /**
     * 标题
     */
    @NotBlank(message = "文章标题不能为空")
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
    @NotBlank(message = "文章内容不能为空")
    private String articleContent;

    /**
     * 是否置顶 0否 1是
     */
    @NotNull(message = "文章是否置顶不能为空")
    private Integer articleTop;

    /**
     * 文章状态
     */
    @NotNull(message = "文章状态不能为空")
    private Integer articleStatus;
}
