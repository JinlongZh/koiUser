package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.request.BlogBarrageReqVO;
import com.koi.blog.domain.vo.response.BlogBarrageRespVO;
import com.koi.blog.service.BlogBarrageService;
import com.koi.common.domain.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 留言API
 *
 * @Author zjl
 * @Date 2024/10/13
 */
@Tag(name = "留言API")
@RestController
@RequestMapping("/blog/barrage")
public class BlogBarrageController {

    @Resource
    private BlogBarrageService blogBarrageService;

    /**
     * 查看留言列表
     *
     * @param
     * @Return Result<List<MessageDTO>>
     * @Date 2022/9/25 11:05
     */
    @Operation(summary = "查看留言列表")
    @GetMapping("/list")
    public CommonResult<List<BlogBarrageRespVO>> queryMessages() {
        return CommonResult.success(blogBarrageService.queryMessages());
    }

    /**
     * 添加留言
     *
     * @param messageVO
     * @Return Result<?>
     * @Date 2022/9/25 11:23
     */
    @Operation(summary = "添加留言")
    @PostMapping("/add")
    public CommonResult<?> addMessage(@Valid @RequestBody BlogBarrageReqVO messageVO) {
        blogBarrageService.addMessage(messageVO);
        return CommonResult.success("添加成功");
    }

}
