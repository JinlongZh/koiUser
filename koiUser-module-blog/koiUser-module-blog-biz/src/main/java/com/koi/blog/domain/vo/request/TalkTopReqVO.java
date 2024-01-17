package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/17 16:54
 */
@Data
public class TalkTopReqVO {

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
