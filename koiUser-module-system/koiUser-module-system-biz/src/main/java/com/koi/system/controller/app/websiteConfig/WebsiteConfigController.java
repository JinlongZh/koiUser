package com.koi.system.controller.app.websiteConfig;

import com.koi.common.domain.CommonResult;
import com.koi.system.service.websiteConfig.WebsiteConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.Map;

/**
 * 网站配置
 *
 * @Author zjl
 * @Date 2024/1/24 20:12
 */
@Tag(name = "网站配置")
@RestController
@RequestMapping("/system/config")
public class WebsiteConfigController {

    @Resource
    private WebsiteConfigService websiteConfigService;

    @PermitAll
    @Operation(summary = "获取网站配置")
    @GetMapping("/get")
    public CommonResult<Map<String, String>> getWebsiteConfig() {
        return CommonResult.success(websiteConfigService.getWebsiteConfig());
    }

}
