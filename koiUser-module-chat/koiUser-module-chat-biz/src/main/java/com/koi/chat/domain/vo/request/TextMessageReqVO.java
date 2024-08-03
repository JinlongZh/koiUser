package com.koi.chat.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 文本消息 request vo
 *
 * @Author zjl
 * @Date 2023/10/9 15:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageReqVO {

    @NotBlank(message = "内容不能为空")
    @Size(max = 1024, message = "消息内容过长")
    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "回复的消息id,如果没有别传就好")
    private Long replyMessageId;

}

