package com.koi.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记录和前端连接的一些映射信息
 *
 * @Author zjl
 * @Date 2023/9/30 21:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSChannelExtraDTO {

    /**
     * 前端如果登录了，记录uid
     */
    private Long userId;

}
