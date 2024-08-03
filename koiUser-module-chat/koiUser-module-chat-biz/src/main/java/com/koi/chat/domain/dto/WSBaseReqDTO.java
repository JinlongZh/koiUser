package com.koi.chat.domain.dto;

import com.koi.chat.enums.WSReqTypeEnum;
import lombok.Data;

/**
 * websocket前端请求体
 *
 * @Author zjl
 * @Date 2023/9/30 15:34
 */
@Data
public class WSBaseReqDTO {

    /**
     * 请求类型
     *
     * @see WSReqTypeEnum
     */
    private Integer type;

    /**
     * 每个请求包具体的数据，类型不同结果不同
     */
    private String data;

}
