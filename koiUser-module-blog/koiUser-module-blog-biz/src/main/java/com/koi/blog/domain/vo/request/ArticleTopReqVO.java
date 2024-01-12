package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 文章指定req-vo
 *
 * @Author zjl
 * @Date 2024/1/11 17:23
 */
@Data
public class ArticleTopReqVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 置顶状态
     */
    @NotNull(message = "置顶状态不能为空")
    private Integer top;

}
