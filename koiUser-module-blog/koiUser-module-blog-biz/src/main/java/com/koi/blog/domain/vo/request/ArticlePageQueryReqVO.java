package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.*;

/**
 * 文章查询 req-vo
 *
 * @Author zjl
 * @Date 2023/12/17 11:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageQueryReqVO extends PageParam {

    /**
     * 分类id
     */
    private String CategoryId;

}
