package com.koi.blog.constants;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 友链状态枚举
 *
 * @Author zjl
 * @Date 2024/7/30
 */
@AllArgsConstructor
@Getter
public enum FriendLinkStatusEnum {

    PUBLIC(0, "公开"),
    CLOSE(1, "关闭"),
    AUDIT(2, "审核中"),
    REJECT(3, "审核拒绝"),
    ;

    /**
     * 状态
     */
    private final Integer status;
    /**
     * 描述
     */
    private final String description;

    public static FriendLinkStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(friendLinkStatusEnum -> friendLinkStatusEnum.getStatus().equals(value), FriendLinkStatusEnum.values());
    }


}
