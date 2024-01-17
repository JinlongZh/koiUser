package com.koi.blog.controller.admin;

import com.koi.blog.domain.entity.Talk;
import com.koi.blog.domain.vo.request.TalkAdminAddReqVO;
import com.koi.blog.domain.vo.request.TalkAdminQueryReqVO;
import com.koi.blog.domain.vo.request.TalkAdminUpdateReqVO;
import com.koi.blog.domain.vo.request.TalkTopReqVO;
import com.koi.blog.domain.vo.response.TalkAdminRespVO;
import com.koi.blog.service.TalkService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.json.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/17 16:49
 */
@Tag(name = "后台-说说")
@RestController
@RequestMapping("/blog/talk")
public class AdminTalkController {

    @Resource
    private TalkService talkService;

    @PermitAll
    @Operation(summary = "分页获取说说列表")
    @GetMapping("/page")
    public CommonResult<PageResult<TalkAdminRespVO>> pageTalk(TalkAdminQueryReqVO req) {
        return CommonResult.success(talkService.pageTalkAdmin(req));
    }

    @PermitAll
    @Operation(summary = "添加说说")
    @PostMapping("/add")
    public CommonResult<String> addTalk(@RequestBody @Valid TalkAdminAddReqVO req) {
        talkService.addTalk(req);
        return CommonResult.success("添加成功");
    }

    @PermitAll
    @Operation(summary = "更新说说")
    @PostMapping("/update")
    public CommonResult<String> updateTalk(@RequestBody @Valid TalkAdminUpdateReqVO req) {
        talkService.updateTalk(req);
        return CommonResult.success("更新成功");
    }

    @PermitAll
    @Operation(summary = "删除说说")
    @DeleteMapping("/delete")
    public CommonResult<String> deleteTalk(@RequestParam("id") Long talkId) {
        talkService.deleteTalk(talkId);
        return CommonResult.success("删除成功");
    }

    @PermitAll
    @Operation(summary = "更改说说置顶")
    @PutMapping("/top")
    public CommonResult<String> updateTalkTop(@RequestBody @Valid TalkTopReqVO req) {
        talkService.updateTalkTop(req);
        return CommonResult.success("修改成功");
    }

    @PermitAll
    @Operation(summary = "获取说说详情")
    @GetMapping("/detail")
    public CommonResult<TalkAdminRespVO> getTalk(@RequestParam("id") Long talkId) {
        Talk talk = talkService.getTalkById(talkId);
        TalkAdminRespVO talkAdminRespVO = BeanCopyUtils.copyObject(talk, TalkAdminRespVO.class);
        // 处理图片，将字符串转换为数组
        talkAdminRespVO.setImageList(CollectionUtils.castList(JsonUtils.parseObject(talkAdminRespVO.getImages(), List.class), String.class));
        return CommonResult.success(talkAdminRespVO);
    }


}
