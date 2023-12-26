package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.CommentAddReqVO;
import com.koi.blog.domain.vo.request.CommentQueryReqVO;
import com.koi.blog.domain.vo.request.ReplyQueryReqVO;
import com.koi.blog.domain.vo.response.CommentRespVO;
import com.koi.blog.domain.vo.response.ReplyRespVO;
import com.koi.blog.service.CommentService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

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


    @Operation(summary = "添加评论")
    @PostMapping("/add")
    public CommonResult<String> addComment(CommentAddReqVO req) {
        commentService.addComment(req, SecurityFrameworkUtils.getLoginUserId());
        return CommonResult.success("添加成功");
    }

    @PermitAll
    @Operation(summary = "查询评论下的回复")
    @GetMapping("/reply/page")
    public CommonResult<List<ReplyRespVO>> pageCommentReply(ReplyQueryReqVO req) {
        return CommonResult.success(commentService.pageCommentReply(req));
    }

}
