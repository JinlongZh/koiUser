package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.HomeListQueryReqVO;
import com.koi.blog.domain.vo.response.HomeListRespVO;
import com.koi.blog.service.HomeService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

/**
 * 主页API
 *
 * @Author zjl
 * @Date 2023/12/17 12:39
 */
@Tag(name = "主页API")
@RestController
@RequestMapping("/blog/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @PermitAll
    @Operation(summary = "获取首页内容列表")
    @GetMapping("/list")
    public CommonResult<PageResult<HomeListRespVO>> pageHomeList(HomeListQueryReqVO reqVO) {
        return CommonResult.success(homeService.pageHomeList(reqVO));
    }

}
