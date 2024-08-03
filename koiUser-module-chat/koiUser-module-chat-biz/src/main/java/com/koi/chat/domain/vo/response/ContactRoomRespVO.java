package com.koi.chat.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息列表 response vo
 *
 * @Author zjl
 * @Date 2023/10/16 18:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRoomRespVO {


    /**
     * 房间id
     */
    private Long roomId;


    /**
     * 房间类型 1群聊 2单聊
     */
    private Integer type;

    /**
     * 最新消息
     */
    private String text;

    /**
     * 会话名称
     */
    private String name;

    /**
     * 会话头像
     */
    private String avatar;

    /**
     * 房间最后活跃时间(用来排序)
     */
    private LocalDateTime activeTime;

    /**
     * 未读数
     */
    private Integer unreadCount;

}
