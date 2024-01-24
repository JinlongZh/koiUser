package com.koi.system.controller.admin.websiteConfig;

import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.domain.websiteConfig.entity.WebsiteConfig;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigAddReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigPageReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigUpdateReqVO;
import com.koi.system.domain.websiteConfig.vo.response.WebsiteConfigRespVO;
import com.koi.system.service.websiteConfig.WebsiteConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * 管理后台 - 网站配置
 *
 * @Author zjl
 * @Date 2024/1/24 20:12
 */
@Tag(name = "管理后台 - 网站配置")
@RestController
@RequestMapping("/system/config")
public class AdminWebsiteConfigController {

    @Resource
    private WebsiteConfigService websiteConfigService;

    @PermitAll
    @Operation(summary = "新增参数配置")
    @PostMapping("/add")
    public CommonResult<Boolean> addConfig(@Valid @RequestBody WebsiteConfigAddReqVO req) {
        websiteConfigService.addConfig(req);
        return CommonResult.success(true);
    }

    @PermitAll
    @Operation(summary = "更新参数配置")
    @PostMapping("/update")
    public CommonResult<Boolean> updateConfig(@RequestBody @Valid WebsiteConfigUpdateReqVO req) {
        websiteConfigService.updateConfig(req);
        return CommonResult.success(true);
    }

    @PermitAll
    @Operation(summary = "获取参数配置详情")
    @GetMapping("/detail")
    public CommonResult<WebsiteConfigRespVO> getConfigDetail(@RequestParam("id") Long id) {
        WebsiteConfig websiteConfig = websiteConfigService.getWebsiteConfigById(id);
        WebsiteConfigRespVO websiteConfigRespVO = BeanCopyUtils.copyObject(websiteConfig, WebsiteConfigRespVO.class);
        return CommonResult.success(websiteConfigRespVO);
    }

    @PermitAll
    @Operation(summary = "获得参数配置分页")
    @GetMapping("/page")
    public CommonResult<PageResult<WebsiteConfigRespVO>> getConfigPage(@Valid WebsiteConfigPageReqVO reqVO) {
        return CommonResult.success(websiteConfigService.getConfigPage(reqVO));
    }

}
