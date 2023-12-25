package com.koi.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 子评论数量
 *
 * @Author zjl
 * @Date 2023/12/25 18:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCountDTO {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 回复数量
     */
    private Long replyCount;

}
