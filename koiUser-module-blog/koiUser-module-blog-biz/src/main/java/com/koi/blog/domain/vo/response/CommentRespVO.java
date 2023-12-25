package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论resp-vo
 *
 * @Author zjl
 * @Date 2023/12/25 17:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRespVO {

    /**
     * 评论id
     */
    private Long id;

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
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;

    /**
     * 回复量
     */
    private Long replyCount;

    /**
     * 回复列表
     */
    private List<ReplyRespVO> replyList;


}
