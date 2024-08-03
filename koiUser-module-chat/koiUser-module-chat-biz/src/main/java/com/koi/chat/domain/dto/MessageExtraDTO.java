package com.koi.chat.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.koi.chat.domain.vo.request.ImageMessageReqVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 消息扩展属性
 *
 * @Author zjl
 * @Date 2023/10/10 17:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageExtraDTO implements Serializable {

    /**
     * 艾特的uid
     */
    private List<Long> atUserIdList;

    /**
     * 图片消息
     */
    private ImageMessageReqVO imageMessageReqVO;

    private static final long serialVersionUID = 1L;

}
