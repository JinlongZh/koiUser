package com.koi.blog.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.blog.domain.entity.HomeList;
import com.koi.blog.domain.vo.request.HomeListQueryReqVO;
import com.koi.common.domain.PageResult;
import com.koi.common.enums.CommonStatusEnum;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.framework.mybatis.utils.MyBatisUtils;

public interface HomeListMapper extends BaseMapper<HomeList> {

    default PageResult<HomeList> pageHomeList(HomeListQueryReqVO reqVO) {
        Page<HomeList> page = MyBatisUtils.buildPage(reqVO);
        Page<HomeList> homeListPage = selectPage(page, new LambdaQueryWrapperX<HomeList>()
                .eq(HomeList::getStatus, CommonStatusEnum.ENABLE)
                .orderByDesc(HomeList::getCreateTime)
                .orderByDesc(HomeList::getUpdateTime));
        return new PageResult<>(homeListPage.getRecords(), homeListPage.getTotal());
    }
}




