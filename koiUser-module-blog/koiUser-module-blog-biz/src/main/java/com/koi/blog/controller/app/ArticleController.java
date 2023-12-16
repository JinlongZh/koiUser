package com.koi.blog.controller.app;

import com.koi.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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


}
