package com.koi.chat.domain.vo.request;

import com.koi.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 消息页面req-vo
 *
 * @Author zjl
 * @Date 2023/10/29 18:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessagePageReqVO extends PageParam {

    @NotNull(message = "房间Id不能为null")
    @Schema(description = "房间ID")
    private Long roomId;

}
