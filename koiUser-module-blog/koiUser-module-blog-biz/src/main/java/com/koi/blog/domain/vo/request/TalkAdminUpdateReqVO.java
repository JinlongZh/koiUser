package com.koi.blog.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/17 16:54
 */
@Data
public class TalkAdminUpdateReqVO {

    /**
     * 说说id
     */
    @NotNull(message = "说说id不能为空")
    private Long id;

    /**
     * 说说内容
     */
    @NotBlank(message = "说说内容不能为空")
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 是否置顶
     */
    @NotNull(message = "是否置顶不能为空")
    private Integer talkTop;

    /**
     * 状态 0公开 1关闭
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

}
