package com.koi.koiuserserver.domain.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * token返回
 *
 * @Author zjl
 * @Date 2023/7/28 11:40
 */
@Builder
@Data
public class TokenResp {

    private String accessToken;

}
