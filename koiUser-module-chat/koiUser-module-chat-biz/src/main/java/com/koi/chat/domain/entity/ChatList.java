package com.koi.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天列表
 * @TableName chat_list
 */
@TableName(value ="chat_list")
@Data
public class ChatList implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 聊天对象id
     */
    private Integer friendId;

    /**
     * 消息未读数量
     */
    private Integer unReadNum;

    /**
     * 最后一条消息id
     */
    private Long lastMessageId;

    /**
     * 最后一条消息内容
     */
    private String lastMessageContent;

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