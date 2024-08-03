package com.koi.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zjl
 * @Date 2023/9/30 20:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSAuthorize {

    /**
     * accessToken
     */
    private String token;

}
