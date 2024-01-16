package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分类更新req-vo
 *
 * @Author zjl
 * @Date 2024/1/16 17:21
 */
@Data
public class CategoryUpdateReqVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    private String categoryName;

}
