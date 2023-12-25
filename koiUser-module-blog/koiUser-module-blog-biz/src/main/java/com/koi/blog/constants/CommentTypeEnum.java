package com.koi.blog.constants;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论类型枚举
 *
 * @Author zjl
 * @Date 2023/12/25 17:18
 */
@AllArgsConstructor
@Getter
public enum CommentTypeEnum {

    ARTICLE(1, "文章"),
    TALK(2, "说说");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型名
     */
    private final String description;

    public static CommentTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(commentType -> commentType.getType().equals(value), CommentTypeEnum.values());
    }

}
