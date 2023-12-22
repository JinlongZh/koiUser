package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.TalkPageQueryReqVO;
import com.koi.blog.domain.vo.response.TalkRespVO;
import com.koi.blog.service.TalkService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

/**
 * 说说API
 *
 * @Author zjl
 * @Date 2023/12/16 21:48
 */
@Tag(name = "说说API")
@RestController
@RequestMapping("/blog/talk")
public class TalkController {

    @Resource
    private TalkService talkService;

    @PermitAll
    @Operation(summary = "获取说说列表")
    @GetMapping("/list")
    public CommonResult<PageResult<TalkRespVO>> pageTalk(TalkPageQueryReqVO req) {
        return CommonResult.success(talkService.pageTalk(req));
    }

    @PermitAll
    @Operation(summary = "获取说说详情")
    @GetMapping("/detail")
    public CommonResult<TalkRespVO> getTalkDetail(@RequestParam("id") Long id) {
        return CommonResult.success(talkService.getTalkDetail(id));
    }

}
