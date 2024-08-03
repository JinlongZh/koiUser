package com.koi.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天信息
 * @TableName chat_message
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="chat_message")
@Data
public class ChatMessage implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 发送方
     */
    private Long fromUserId;

    /**
     * 内容
     */
    private String content;

    /**
     * 回复的消息id
     */
    private Long replyMessageId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 消息扩展字段
     */
    private String extra;

    /**
     * 消息状态 0正常 1删除
     */
    private Integer status;

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