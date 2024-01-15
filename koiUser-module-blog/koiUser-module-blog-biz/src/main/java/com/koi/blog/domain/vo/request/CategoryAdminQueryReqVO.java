package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/15 17:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryAdminQueryReqVO extends PageParam {

    /**
     * 关键词
     */
    private String keywords;

}
