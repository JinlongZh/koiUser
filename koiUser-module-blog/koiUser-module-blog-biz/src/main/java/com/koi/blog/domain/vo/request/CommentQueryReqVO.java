package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.*;

/**
 * 评论查询req-vo
 *
 * @Author zjl
 * @Date 2023/12/25 17:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentQueryReqVO extends PageParam {

    private Integer commentType;

    private Long topicId;

}
