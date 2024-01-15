package com.koi.blog.constants;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 *
 * @Author zjl
 * @Date 2024/1/13 17:01
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    /**
     * 公开
     */
    PUBLIC(0, "公开"),
    /**
     * 私密
     */
    SECRET(1, "私密"),
    /**
     * 草稿
     */
    DRAFT(2, "草稿");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;

    public static ArticleStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(articleStatusEnum -> articleStatusEnum.getStatus().equals(value), ArticleStatusEnum.values());
    }

}
