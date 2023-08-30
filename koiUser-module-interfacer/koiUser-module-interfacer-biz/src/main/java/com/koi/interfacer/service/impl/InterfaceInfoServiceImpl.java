package com.koi.interfacer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.interfacer.domain.entity.InterfaceInfo;
import com.koi.interfacer.domain.vo.request.InterfaceInfoPageReqVO;
import com.koi.interfacer.domain.vo.response.InterfaceInfoRespVO;
import com.koi.interfacer.mapper.InterfaceInfoMapper;
import com.koi.interfacer.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接口信息 Service 实现类
 *
 * @Author zjl
 * @Date 2023/8/29 21:04
 */
@Slf4j
@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public PageResult<InterfaceInfoRespVO> getInterfaceInfoPage(InterfaceInfoPageReqVO pageReqVO) {
        PageResult<InterfaceInfo> pageResult = interfaceInfoMapper.selectInterfaceInfoPage(pageReqVO);
        List<InterfaceInfo> interfaceInfoList = pageResult.getList();
        List<InterfaceInfoRespVO> interfaceInfoRespVOList = BeanCopyUtils.copyList(interfaceInfoList, InterfaceInfoRespVO.class);
        return new PageResult<>(interfaceInfoRespVOList, pageResult.getTotal());

    }

    @Override
    public InterfaceInfo getInterfaceInfoById(Long id) {
        return interfaceInfoMapper.selectById(id);
    }
}
