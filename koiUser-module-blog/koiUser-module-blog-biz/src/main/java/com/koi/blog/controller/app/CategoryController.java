package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.blog.service.CategoryService;
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
 * 文章分类API
 *
 * @Author zjl
 * @Date 2023/12/16 21:46
 */
@Tag(name = "文章分类API")
@RestController
@RequestMapping("/blog/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 查看分类列表
     *
     * @param
     * @Return Result<PageResult<CategoryDTO>>
     * @Date 2022/9/23 21:34
     */
    @PermitAll
    @Operation(summary = "查看分类列表")
    @GetMapping("/list")
    public CommonResult<PageResult<CategoryRespVO>> listCategories() {
        return CommonResult.success(categoryService.listCategories());
    }

}
