package com.koi.blog.domain.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言返回vo
 *
 * @Author zjl
 * @Date 2024/10/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogBarrageRespVO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 弹幕速度
     */
    private Integer time;

}
