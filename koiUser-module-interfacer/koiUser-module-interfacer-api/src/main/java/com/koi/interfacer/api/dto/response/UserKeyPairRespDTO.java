package com.koi.interfacer.api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户秘钥对 response vo
 *
 * @Author zjl
 * @Date 2023/8/31 15:19
 */
@Data
public class UserKeyPairRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 公钥
     */
    private String accessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 附加信息
     */
    private String additionalInformation;

}
