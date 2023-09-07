package com.koi.interfacer.controller.app;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.interfacer.client.InterfacerClient;
import com.koi.interfacer.domain.entity.InterfaceInfo;
import com.koi.interfacer.domain.vo.request.InterfaceInfoInvokeReqVO;
import com.koi.interfacer.domain.vo.request.InterfaceInfoPageReqVO;
import com.koi.interfacer.domain.vo.response.InterfaceInfoRespVO;
import com.koi.interfacer.service.InterfaceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Objects;

import static com.koi.common.enums.CommonStatusEnum.DISABLE;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.*;
import static com.koi.interfacer.convert.InterfaceInfoConvert.convertInterfaceInfo;

/**
 * 接口信息
 *
 * @Author zjl
 * @Date 2023/8/29 20:54
 */
@Tag(name = "前台 接口信息")
@RestController
@RequestMapping("/interface")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private InterfacerClient interfacerClient;

    /**
     * 获得接口信息分页
     *
     * @param pageReqVO
     * @Return CommonResult<PageResult < InterfaceInfoRespVO>>
     */
    @PermitAll
    @GetMapping("/page")
    @Operation(summary = "获得接口信息分页")
    public CommonResult<PageResult<InterfaceInfoRespVO>> getInterfaceInfoPage(@Valid InterfaceInfoPageReqVO pageReqVO) {
        return CommonResult.success(interfaceInfoService.getInterfaceInfoPage(pageReqVO));
    }

    /**
     * 根据id获取接口信息
     *
     * @param id
     * @Return CommonResult<InterfaceInfoRespVO>
     */
    @PermitAll
    @GetMapping("/get")
    @Operation(summary = "根据id获取接口信息")
    public CommonResult<InterfaceInfoRespVO> getInterfaceInfo(@RequestParam("id") Long id) {
        InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfoById(id);
        return CommonResult.success(convertInterfaceInfo(interfaceInfo));
    }

    /**
     * 测试调用
     *
     * @param interfaceInfoInvokeReq
     * @Return CommonResult<Object>
     */
    @PermitAll
    @PostMapping(value = "/invoke")
    @Operation(summary = "测试调用")
    public CommonResult<Object> invokeInterfaceInfo(@Valid @RequestBody InterfaceInfoInvokeReqVO interfaceInfoInvokeReq) {
        InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfoById(interfaceInfoInvokeReq.getId());
        if (interfaceInfo == null) {
            throw new ServiceException(BAD_REQUEST);
        }
        if (Objects.equals(interfaceInfo.getStatus(), DISABLE.getStatus())) {
            throw new ServiceException(NOT_IMPLEMENTED.getCode(), "接口已关闭");
        }

        // 开始调用
        String invokeResult;
        try {
            invokeResult = interfacerClient.invokeInterface(interfaceInfoInvokeReq.getRequestParams(), interfaceInfo.getHost(), interfaceInfo.getUrl(), HttpMethod.valueOf(interfaceInfo.getMethod()));
            if (StringUtils.isBlank(invokeResult)) {
                throw new ServiceException(INTERNAL_SERVER_ERROR.getCode(), "接口数据为空");
            }
        } catch (Exception e) {
            throw new ServiceException(INTERNAL_SERVER_ERROR.getCode(), "接口验证失败");
        }
        return CommonResult.success(invokeResult);
    }

}
