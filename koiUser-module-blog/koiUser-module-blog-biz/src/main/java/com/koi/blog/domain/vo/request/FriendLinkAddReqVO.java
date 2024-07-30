package com.koi.blog.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 添加友链 req VO
 *
 * @Author zjl
 * @Date 2024/7/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendLinkAddReqVO {

    /**
     * 友链名
     */
    @NotBlank(message = "友链名不能为空")
    private String linkName;

    /**
     * 友链头像
     */
    @NotBlank(message = "链接头像不能为空")
    private String linkAvatar;

    /**
     * 友链地址
     */
    @NotBlank(message = "友链地址不能为空")
    private String linkAddress;

    /**
     * 友链介绍
     */
    @NotBlank(message = "友链介绍不能为空")
    private String linkIntro;

}
