package com.koi.chat.domain.vo.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.koi.framework.jackson.core.databind.LocalDateTimeDeserializer;
import com.koi.framework.jackson.core.databind.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天信息 response vo
 *
 * @Author zjl
 * @Date 2023/10/8 22:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRespVO {

    /**
     * 发送者信息
     */
    private UserInfo fromUser;

    private Message message;

    @Data
    public static class UserInfo {

        /**
         * 用户id
         */
        private Long userId;
    }

    @Data
    public static class Message {

        /**
         * 消息id
         */
        private Long id;

        /**
         * 房间id
         */
        private Long roomId;

        /**
         * 消息类型
         */
        private Integer type;

        /**
         * 消息内容不同的消息类型，内容体不同
         */
        private Object body;

        /**
         * 创建时间
         */
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime createTime;
    }

}
