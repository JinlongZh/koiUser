package com.koi.blog.controller.admin;

import com.koi.blog.domain.vo.request.ArticleAdminAddReqVO;
import com.koi.blog.domain.vo.request.ArticleAdminQueryReqVO;
import com.koi.blog.domain.vo.request.ArticleAdminUpdateReqVO;
import com.koi.blog.domain.vo.response.ArticleAdminRespVO;
import com.koi.blog.service.ArticleService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/10 17:55
 */
@Tag(name = "后台-文章")
@RestController
@RequestMapping("/blog/article")
public class AdminArticleController {

    @Resource
    private ArticleService articleService;

    @PermitAll
    @Operation(summary = "分页获取文章列表")
    @GetMapping("/page")
    public CommonResult<PageResult<ArticleAdminRespVO>> pageArticle(ArticleAdminQueryReqVO req) {
        return CommonResult.success(articleService.pageArticle(req));
    }

    @PermitAll
    @Operation(summary = "添加文章")
    @PostMapping("/add")
    public CommonResult<String> addArticle(@RequestBody @Valid ArticleAdminAddReqVO req) {
        articleService.addArticle(req);
        return CommonResult.success("添加成功");
    }

    @PermitAll
    @Operation(summary = "更新文章")
    @PostMapping("/update")
    public CommonResult<String> updateArticle(@RequestBody @Valid ArticleAdminUpdateReqVO req) {
        articleService.updateArticle(req);
        return CommonResult.success("更新成功");
    }

    @PermitAll
    @Operation(summary = "删除文章")
    @DeleteMapping("/delete")
    public CommonResult<String> deleteArticle(@RequestParam("id") Long articleId) {
        articleService.deleteArticle(articleId);
        return CommonResult.success("删除成功");
    }
}
