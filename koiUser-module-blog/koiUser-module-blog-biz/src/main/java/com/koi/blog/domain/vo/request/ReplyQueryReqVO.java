package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 回复查询req-vo
 *
 * @Author zjl
 * @Date 2023/12/26 16:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReplyQueryReqVO extends PageParam {

    @NotNull(message = "评论id不能为空")
    @Schema(description = "评论id")
    private Long commentId;

}
