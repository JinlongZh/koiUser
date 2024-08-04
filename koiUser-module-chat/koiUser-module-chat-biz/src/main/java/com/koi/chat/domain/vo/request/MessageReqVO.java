package com.koi.chat.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 聊天信息 request vo
 *
 * @Author zjl
 * @Date 2023/10/8 22:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReqVO {

    @Schema(description = "房间id")
    @NotNull(message = "房间id不能为空")
    private Long roomId;

    @Schema(description = "消息类型")
    @NotNull(message = "消息类型不能为空")
    private Integer messageType;

    @Schema(description = "消息内容，类型不同传值不同")
    @NotNull(message = "消息内容不能为空")
    private Object body;

}
