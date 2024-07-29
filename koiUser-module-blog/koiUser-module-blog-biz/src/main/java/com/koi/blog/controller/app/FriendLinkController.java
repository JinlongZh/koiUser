package com.koi.blog.controller.app;

import com.koi.blog.domain.vo.response.FriendLinkRespVO;
import com.koi.blog.service.FriendLinkService;
import com.koi.common.domain.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * 友链
 *
 * @Author zjl
 * @Date 2024/7/29
 */
@Tag(name = "友链")
@RestController
@RequestMapping("/blog/friend-link")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    /**
     * 查看友链列表
     *
     * @param
     * @Return Result<List<FriendLinkDTO>>
     * @Date 2022/9/25 9:53
     */
    @PermitAll
    @Operation(summary = "查看友链列表")
    @GetMapping("/links")
    public CommonResult<List<FriendLinkRespVO>> listFriendLinks() {
        return CommonResult.success(friendLinkService.listFriendLinks());
    }

}
