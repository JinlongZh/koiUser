package com.koi.blog.domain.vo.request;

import com.koi.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/17 16:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TalkAdminQueryReqVO extends PageParam {

    /**
     * 说说状态
     */
    private Integer status;

}
