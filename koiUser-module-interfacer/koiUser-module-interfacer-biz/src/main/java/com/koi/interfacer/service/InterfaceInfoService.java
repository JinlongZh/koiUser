package com.koi.interfacer.service;

import com.koi.common.domain.PageResult;
import com.koi.interfacer.domain.entity.InterfaceInfo;
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

    /**
     * 根据id获取接口信息
     *
     * @param id
     * @Return InterfaceInfo
     */
    InterfaceInfo getInterfaceInfoById(Long id);

    /**
     * 查询接口是否存在（请求路径、请求方法、请求参数）
     *
     * @param path
     * @param method
     * @Return InterfaceInfo
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
