package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 分类添加req-vo
 *
 * @Author zjl
 * @Date 2024/1/16 17:20
 */
@Data
public class CategoryAddReqVO {

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String categoryName;

}
