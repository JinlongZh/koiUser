package com.koi.interfacer.api;

import com.koi.common.domain.CommonResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.interfacer.api.dto.response.InterfaceInfoRespDTO;
import com.koi.interfacer.domain.entity.InterfaceInfo;
import com.koi.interfacer.service.InterfaceInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供 RESTful API 接口，给 Feign 调用
 *
 * @Author zjl
 * @Date 2023/9/1 16:06
 */
@Validated
@RestController
public class InterfaceInfoApiImpl implements InterfaceInfoApi{

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public CommonResult<InterfaceInfoRespDTO> getInterfaceInfo(String path, String method) {
        InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfo(path, method);
        return CommonResult.success(BeanCopyUtils.copyObject(interfaceInfo, InterfaceInfoRespDTO.class));
    }
}
