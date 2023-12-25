package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 子评论resp-vo
 *
 * @Author zjl
 * @Date 2023/12/25 18:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRespVO {

    /**
     * 评论id
     */
    private Long id;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 被回复用户id
     */
    private Long replyUserId;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

}
