package com.koi.blog.controller.admin;

import com.koi.blog.domain.entity.Article;
import com.koi.blog.domain.entity.Category;
import com.koi.blog.domain.vo.request.ArticleAdminAddReqVO;
import com.koi.blog.domain.vo.request.ArticleAdminQueryReqVO;
import com.koi.blog.domain.vo.request.ArticleAdminUpdateReqVO;
import com.koi.blog.domain.vo.request.ArticleTopReqVO;
import com.koi.blog.domain.vo.response.ArticleAdminRespVO;
import com.koi.blog.service.ArticleService;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

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
    @Resource
    private CategoryService categoryService;

    @PermitAll
    @Operation(summary = "分页获取文章列表")
    @GetMapping("/page")
    public CommonResult<PageResult<ArticleAdminRespVO>> pageArticle(ArticleAdminQueryReqVO req) {
        return CommonResult.success(articleService.pageArticleAdmin(req));
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

    @PermitAll
    @Operation(summary = "更改文章置顶")
    @PutMapping("/top")
    public CommonResult<String> updateArticleTop(@RequestBody @Valid ArticleTopReqVO req) {
        articleService.updateArticleTop(req);
        return CommonResult.success("修改成功");
    }

    @PermitAll
    @Operation(summary = "获取文章详情")
    @GetMapping("/detail")
    public CommonResult<ArticleAdminRespVO> getArticleAdminDetail(@RequestParam("id") Long articleId) {
        Article article = articleService.getArticleById(articleId);
        if (article == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "文章不存在");
        }
        ArticleAdminRespVO articleAdminRespVO = BeanCopyUtils.copyObject(article, ArticleAdminRespVO.class);
        // 草稿可能没有分类
        if (article.getCategoryId() != null){
            Category category = categoryService.getCategoryById(article.getCategoryId());
            articleAdminRespVO.setCategoryName(category.getCategoryName());
        }
        return CommonResult.success(articleAdminRespVO);
    }
}
