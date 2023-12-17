package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.ArticlePageQueryReqVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.blog.service.ArticleService;
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
 * 文章API
 *
 * @Author zjl
 * @Date 2023/12/16 21:49
 */
@Tag(name = "文章API")
@RestController
@RequestMapping("/blog/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PermitAll
    @Operation(summary = "获取文章列表")
    @GetMapping("/list")
    public CommonResult<PageResult<ArticleRespVO>> pageHomeArticle(ArticlePageQueryReqVO reqVO) {
        PageResult<ArticleRespVO> articleRespVOPageResult = articleService.pageHomeArticle(reqVO);
        return CommonResult.success(articleRespVOPageResult);
    }


}
