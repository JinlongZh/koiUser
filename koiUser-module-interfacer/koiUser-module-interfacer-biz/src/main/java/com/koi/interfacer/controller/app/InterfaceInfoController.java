package com.koi.interfacer.controller.app;

import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.interfacer.domain.vo.request.InterfaceInfoPageReqVO;
import com.koi.interfacer.domain.vo.response.InterfaceInfoRespVO;
import com.koi.interfacer.service.InterfaceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * 接口信息
 *
 * @Author zjl
 * @Date 2023/8/29 20:54
 */
@Tag(name = "前台 接口信息")
@RestController("/interface")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 获得接口信息分页
     *
     * @param pageReqVO
     * @Return CommonResult<PageResult<InterfaceInfoRespVO>>
     */
    @PermitAll
    @GetMapping("/page")
    @Operation(summary = "获得接口信息分页")
    public CommonResult<PageResult<InterfaceInfoRespVO>> getInterfaceInfoPage(@ParameterObject @Valid InterfaceInfoPageReqVO pageReqVO) {
        return CommonResult.success(interfaceInfoService.getInterfaceInfoPage(pageReqVO));
    }

}
