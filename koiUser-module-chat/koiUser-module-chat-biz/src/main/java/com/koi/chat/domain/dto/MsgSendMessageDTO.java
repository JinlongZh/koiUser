package com.koi.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息发送dto
 *
 * @author zjl
 * @date 2023/10/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgSendMessageDTO implements Serializable {

    /**
     * 消息id
     */
    private Long msgId;

}
