package com.koi.chat.domain.dto;

import com.koi.chat.enums.WSRespTypeEnum;
import lombok.Data;

/**
 * ws的基本返回信息体
 *
 * @Author zjl
 * @Date 2023/9/30 15:25
 */
@Data
public class WSBaseRespDTO<T> {
    /**
     * ws推送给前端的消息
     *
     * @see WSRespTypeEnum
     */
    private Integer type;

    private T data;
}
