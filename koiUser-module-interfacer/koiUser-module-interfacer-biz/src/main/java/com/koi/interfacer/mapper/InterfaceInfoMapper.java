package com.koi.interfacer.mapper;

import com.koi.common.domain.PageResult;
import com.koi.framework.mybatis.core.mapper.BaseMapperX;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.interfacer.domain.entity.InterfaceInfo;
import com.koi.interfacer.domain.vo.request.InterfaceInfoPageReqVO;

/**
 * 接口信息 mapper
 *
 * @Author zjl
 * @Date 2023/8/29 14:46:51
 */
public interface InterfaceInfoMapper extends BaseMapperX<InterfaceInfo> {

    default PageResult<InterfaceInfo> selectInterfaceInfoPage(InterfaceInfoPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<InterfaceInfo>()
                .likeIfPresent(InterfaceInfo::getName, pageReqVO.getName())
                .orderByDesc(InterfaceInfo::getId));
    }

    default InterfaceInfo selectInterfaceInfo(String path, String method) {
        return selectOne(new LambdaQueryWrapperX<InterfaceInfo>()
                .eq(InterfaceInfo::getUrl, path)
                .eq(InterfaceInfo::getMethod, method));
    }
}




