package com.koi.blog.service;

import com.koi.blog.domain.vo.request.HomeListQueryReqVO;
import com.koi.blog.domain.vo.response.HomeListRespVO;
import com.koi.common.domain.PageResult;

/**
 * @Author zjl
 * @Date 2023/12/17 12:40
 */
public interface HomeService {

    /**
     * 获取首页内容列表
     *
     * @param reqVO
     * @return {@link PageResult}<{@link HomeListRespVO}>
     */
    PageResult<HomeListRespVO> pageHomeList(HomeListQueryReqVO reqVO);
}
