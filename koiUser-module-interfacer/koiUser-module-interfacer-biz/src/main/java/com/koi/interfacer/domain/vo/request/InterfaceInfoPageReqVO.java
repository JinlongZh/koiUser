package com.koi.interfacer.domain.vo.request;

import com.koi.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 接口信息分页 request vo
 *
 * @Author zjl
 * @Date 2023/8/29 21:12
 */
@Schema(description="接口信息分页 request vo")
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoPageReqVO extends PageParam {

    /**
     * 模糊匹配
     */
    private String name;

}
