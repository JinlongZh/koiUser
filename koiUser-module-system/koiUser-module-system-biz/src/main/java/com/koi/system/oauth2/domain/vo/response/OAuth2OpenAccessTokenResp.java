package com.koi.system.oauth2.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问令牌 Response VO
 *
 * @Author zjl
 * @Date 2023/7/30 15:59
 */
@Schema(description = "访问令牌 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2OpenAccessTokenResp {

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "tudou")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "刷新令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "nice")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(description = "令牌类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "bearer")
    @JsonProperty("token_type")
    private String tokenType;

    @Schema(description = "过期时间,单位：秒", requiredMode = Schema.RequiredMode.REQUIRED, example = "42430")
    @JsonProperty("expires_in")
    private Long expiresIn;

    @Schema(description = "授权范围,如果多个授权范围，使用空格分隔", example = "user_info")
    private String scope;

//    @Schema(description = "授权范围", example = "user_info")
//    private List<String> scope;

}
