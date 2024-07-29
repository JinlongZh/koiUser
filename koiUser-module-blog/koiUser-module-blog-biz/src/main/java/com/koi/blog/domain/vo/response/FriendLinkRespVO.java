package com.koi.blog.domain.vo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 友链返回vo
 *
 * @Author zjl
 * @Date 2024/7/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendLinkRespVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 链接介绍
     */
    private String linkIntro;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
