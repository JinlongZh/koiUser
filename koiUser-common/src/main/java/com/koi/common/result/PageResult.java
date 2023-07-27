package com.koi.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回对象
 *
 * @Author zjl
 * @Date 2023/7/16 10:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {

    /**
     * 分页列表
     */
    private List<T> recordList;

    /**
     * 总数
     */
    private Integer count;

}