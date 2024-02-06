package com.koi.system.controller.admin.websiteConfig;

import com.koi.system.service.websiteConfig.LogVisitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理后台 - 访问日志
 *
 * @Author zjl
 * @Date 2024/1/29 17:28
 */
@Tag(name = "管理后台 - 访问日志")
@RestController
@RequestMapping("/system/log-visit")
public class AdminLogVisitController {

    @Resource
    private LogVisitService logVisitService;

}
