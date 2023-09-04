package com.koi.interfacer.convert;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.interfacer.domain.dto.RequestParamDTO;
import com.koi.interfacer.domain.dto.ResponseParamDTO;
import com.koi.interfacer.domain.entity.InterfaceInfo;
import com.koi.interfacer.domain.vo.response.InterfaceInfoRespVO;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/4 15:49
 */
public interface InterfaceInfoConvert {


    static InterfaceInfoRespVO convertInterfaceInfo(InterfaceInfo interfaceInfo) {
        InterfaceInfoRespVO interfaceInfoRespVO = BeanCopyUtils.copyObject(interfaceInfo, InterfaceInfoRespVO.class);
        interfaceInfoRespVO.setRequestParamList(JsonUtils.parseList(interfaceInfo.getRequestParams(), RequestParamDTO.class));
        interfaceInfoRespVO.setResponseParamList(JsonUtils.parseList(interfaceInfo.getResponseParams(), ResponseParamDTO.class));
        return interfaceInfoRespVO;
    }

}
