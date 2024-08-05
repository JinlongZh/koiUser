package com.koi.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 房间表
 * @TableName room
 */
@TableName(value ="chat_room")
@Data
public class RoomDO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 房间类型 1群聊 2单聊
     */
    private Integer type;

    /**
     * 群最后消息的更新时间
     */
    private LocalDateTime activeTime;

    /**
     * 会话中的最后一条消息id
     */
    private Long lastMessageId;

    /**
     * 额外信息（根据不同类型房间有不同存储的东西）
     */
    private Object extJson;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}