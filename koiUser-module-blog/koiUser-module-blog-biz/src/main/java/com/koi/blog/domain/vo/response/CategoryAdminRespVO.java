package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台分页resp-vo
 *
 * @Author zjl
 * @Date 2024/1/15 17:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAdminRespVO {

    /**
     * id
     */
    private Long id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 公开文章数量
     */
    private Integer publicArticleCount;

    /**
     * 私密文章数量
     */
    private Integer secretArticleCount;

    /**
     * 草稿文章数量
     */
    private Integer draftArticleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
