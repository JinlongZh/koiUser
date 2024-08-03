package com.koi.chat.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 读取消息req-vo
 *
 * @Author zjl
 * @Date 2023/11/4 17:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadMessageReqVO {

    @NotNull(message = "roomId不能为空")
    @Schema(description = "房间id")
    private Long roomId;

}
