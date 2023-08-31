package com.koi.interfacer.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 接口测试调用 request vo
 *
 * @Author zjl
 * @Date 2023/8/30 20:59
 */
@Schema(description="接口测试调用 request vo")
@Data
public class InterfaceInfoInvokeReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "请求方法", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String method;

    @Schema(description = "请求参数", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String requestParams;

    @Schema(description = "主机号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String host;

}
