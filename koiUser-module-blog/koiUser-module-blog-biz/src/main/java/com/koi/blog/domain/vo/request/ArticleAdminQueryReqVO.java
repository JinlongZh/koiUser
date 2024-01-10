package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 后台文章查询req-vo
 *
 * @Author zjl
 * @Date 2024/1/10 18:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleAdminQueryReqVO extends PageParam {

    /**
     * 查询关键词
     */
    private String keywords;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 状态
     */
    private Integer status;


}
