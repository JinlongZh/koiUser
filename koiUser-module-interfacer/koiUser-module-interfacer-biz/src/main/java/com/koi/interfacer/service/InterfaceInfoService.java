package com.koi.interfacer.service;

import com.koi.common.domain.PageResult;
import com.koi.interfacer.domain.vo.request.InterfaceInfoPageReqVO;
import com.koi.interfacer.domain.vo.response.InterfaceInfoRespVO;

/**
 * 接口信息 Service
 *
 * @Author zjl
 * @Date 2023/8/29 21:04
 */
public interface InterfaceInfoService {

    /**
     * 获得接口信息分页
     *
     * @param pageReqVO
     * @Return PageResult<InterfaceInfoRespVO>
     */
    PageResult<InterfaceInfoRespVO> getInterfaceInfoPage(InterfaceInfoPageReqVO pageReqVO);
}
