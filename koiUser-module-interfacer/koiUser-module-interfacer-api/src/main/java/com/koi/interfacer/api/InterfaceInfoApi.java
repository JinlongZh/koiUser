package com.koi.interfacer.api;

import com.koi.common.domain.CommonResult;
import com.koi.interfacer.api.dto.response.InterfaceInfoRespDTO;
import com.koi.interfacer.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * InterfaceInfo API 接口
 *
 * @Author zjl
 * @Date 2023/9/1 15:23
 */
@FeignClient(name = ApiConstants.NAME)
public interface InterfaceInfoApi {

    String PREFIX = ApiConstants.PREFIX;

    @SuppressWarnings("HttpUrlsUsage")
    String URL_INTERFACE_INFO = "http://" + ApiConstants.NAME + PREFIX + "/getByCondition";

    @GetMapping(PREFIX + "/getByCondition")
    CommonResult<InterfaceInfoRespDTO> getInterfaceInfo(@RequestParam("path") String path,
                                                        @RequestParam("method") String method);

}
