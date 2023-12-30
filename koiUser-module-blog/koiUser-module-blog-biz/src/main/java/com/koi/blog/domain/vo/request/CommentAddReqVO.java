package com.koi.blog.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 添加评论req-vo
 *
 * @Author zjl
 * @Date 2023/12/25 20:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentAddReqVO {

    @Schema(description = "评论类型")
    private Integer type;

    @Schema(description = "评论内容id")
    private Long topicId;

    @NotNull(message = "评论用户id不能为空")
    @Schema(description = "评论内容")
    private String commentContent;

    @Schema(description = "父评论id")
    private Long parentId;

    @Schema(description = "回复用户id")
    private Long replyUserId;

}
