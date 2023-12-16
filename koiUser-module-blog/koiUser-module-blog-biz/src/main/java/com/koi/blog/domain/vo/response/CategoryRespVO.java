package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类 resp-vo
 *
 * @Author zjl
 * @Date 2023/12/16 21:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRespVO {

    /**
     * id
     */
    private Long id;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 分类下的文章数量
     */
    private Integer articleCount;

}
