package com.koi.blog.controller.admin;

import com.koi.blog.domain.vo.request.CategoryAddReqVO;
import com.koi.blog.domain.vo.request.CategoryAdminQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryUpdateReqVO;
import com.koi.blog.domain.vo.response.CategoryAdminRespVO;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.OptionRespVO;
import com.koi.common.domain.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
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

    @PermitAll
    @Operation(summary = "添加分类")
    @PostMapping("/add")
    public CommonResult<String> addCategory(@RequestBody @Valid CategoryAddReqVO req) {
        categoryService.addCategory(req);
        return CommonResult.success("添加成功");
    }

    @PermitAll
    @Operation(summary = "更新分类")
    @PostMapping("/update")
    public CommonResult<String> updateCategory(@RequestBody @Valid CategoryUpdateReqVO req) {
        categoryService.updateCategory(req);
        return CommonResult.success("更新成功");
    }

    @PermitAll
    @Operation(summary = "删除分类")
    @DeleteMapping("/delete")
    public CommonResult<String> deleteCategory(@RequestParam("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return CommonResult.success("删除成功");
    }

}
