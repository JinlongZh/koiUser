package com.koi.framework.mybatis.utils;

import com.koi.common.domain.PageParam;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/12/17 12:07
 */
public class PageUtils {

    public static Integer getStart(PageParam pageParam){
        Integer firstPageNo = 1;
        Integer pageNo = pageParam.getPageNo();
        Integer pageSize = pageParam.getPageSize();

        if (pageNo < firstPageNo) {
            pageNo = firstPageNo;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }
        return (pageNo - 1) * pageSize;
    }

}
