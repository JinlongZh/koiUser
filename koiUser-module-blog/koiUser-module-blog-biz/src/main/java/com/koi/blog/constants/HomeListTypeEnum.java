package com.koi.blog.constants;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 主页列表类型枚举
 *
 * @Author zjl
 * @Date 2023/12/17 12:49
 */
@AllArgsConstructor
@Getter
public enum HomeListTypeEnum {

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

    public static HomeListTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(homeListTypeEnum -> homeListTypeEnum.getType().equals(value), HomeListTypeEnum.values());
    }

}
