package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.CommentQueryReqVO;
import com.koi.blog.domain.vo.response.CommentRespVO;
import com.koi.blog.service.CommentService;
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
 * 评论API
 *
 * @Author zjl
 * @Date 2023/12/25 17:08
 */
@Tag(name = "评论API")
@RestController
@RequestMapping("/blog/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PermitAll
    @Operation(summary = "查询评论")
    @GetMapping("/list")
    public CommonResult<PageResult<CommentRespVO>> listComment(CommentQueryReqVO req) {
        return CommonResult.success(commentService.pageComment(req));
    }

}
