package com.koi.chat.domain.vo.response;

import com.koi.chat.domain.model.UrlInfoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 文本消息返回
 *
 * @Author zjl
 * @Date 2024/11/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageRespVO {

    /**
     * 消息id
     */
    private String content;

    /**
     * 消息链接映射
     */
    private Map<String, UrlInfoModel> urlContentMap;

    /**
     * 被@的用户id列表
     */
    private List<Long> atUserIdList;

    /**
     * 回复消息
     */
    private TextMessageRespVO.ReplyMessage reply;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReplyMessage {

        /**
         * 消息id
         */
        private Long id;

        /**
         * 用户id
         */
        private Long userId;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 消息类型（1 正常文本 2 撤回消息）
         */
        private Integer type;

        /**
         * 消息内容不同的消息类型，见父消息内容体
         */
        private Object body;

        /**
         * 是否可消息跳转 0否 1是
         */
        private Integer canCallback;

        /**
         * 跳转间隔的消息条数
         */
        private Integer gapCount;
    }
}
