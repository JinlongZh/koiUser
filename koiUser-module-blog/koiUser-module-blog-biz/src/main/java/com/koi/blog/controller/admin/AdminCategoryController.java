package com.koi.blog.controller.admin;

import com.koi.blog.domain.vo.request.CategoryAdminQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryQueryReqVO;
import com.koi.blog.domain.vo.response.CategoryAdminRespVO;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.OptionRespVO;
import com.koi.common.domain.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * 后台-文章分类
 *
 * @Author zjl
 * @Date 2024/1/11 16:48
 */
@Tag(name = "后台-文章分类")
@RestController
@RequestMapping("/blog/category")
public class AdminCategoryController {

    @Resource
    private CategoryService categoryService;

    @PermitAll
    @Operation(summary = "获取选项分类")
    @GetMapping("/option")
    public CommonResult<List<OptionRespVO>> getCategoryOption(CategoryQueryReqVO req) {
        return CommonResult.success(categoryService.getCategoryOption(req));
    }

    @PermitAll
    @Operation(summary = "获取后台分类列表")
    @GetMapping("/page")
    public CommonResult<PageResult<CategoryAdminRespVO>> getCategoryList(CategoryAdminQueryReqVO req) {
        return CommonResult.success(categoryService.getCategoryAdminPage(req));
    }

}
