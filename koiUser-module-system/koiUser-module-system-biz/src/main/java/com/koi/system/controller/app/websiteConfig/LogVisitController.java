package com.koi.system.controller.app.websiteConfig;

import com.koi.common.domain.CommonResult;
import com.koi.system.service.websiteConfig.LogVisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/2/4 17:22
 */
@Tag(name = "访问日志")
@RestController
@RequestMapping("/system/log-visit")
public class LogVisitController {

    @Resource
    private LogVisitService logVisitService;

    @PermitAll
    @Operation(summary = "访问上报")
    @PostMapping("/report")
    public CommonResult<Boolean> report(){
        logVisitService.report();
        return CommonResult.success(true);
    }

}
