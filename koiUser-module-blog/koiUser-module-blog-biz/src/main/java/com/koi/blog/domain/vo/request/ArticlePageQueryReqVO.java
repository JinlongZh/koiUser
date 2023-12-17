package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章查询 req-vo
 *
 * @Author zjl
 * @Date 2023/12/17 11:19
 */
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
