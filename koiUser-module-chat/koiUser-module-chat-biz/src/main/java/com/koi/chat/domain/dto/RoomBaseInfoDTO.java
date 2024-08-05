package com.koi.chat.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 房间详情
 *
 * @Author zjl
 * @Date 2023/10/16 19:12
 */
@Data
public class RoomBaseInfoDTO {

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 会话名称
     */
    private String name;

    /**
     * 会话头像
     */
    private String avatar;

    /**
     * 房间类型 1群聊 2单聊
     */
    private Integer type;

    /**
     * 群最后消息的更新时间
     */
    private LocalDateTime activeTime;

    /**
     * 最后一条消息id
     */
    private Long lastMessageId;

}
