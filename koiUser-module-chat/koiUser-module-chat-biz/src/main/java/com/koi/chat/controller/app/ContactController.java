package com.koi.chat.controller.app;

import com.koi.chat.domain.vo.request.ContactPageReqVO;
import com.koi.chat.domain.vo.response.ContactRoomRespVO;
import com.koi.chat.service.ContactService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 会话列表API
 *
 * @Author zjl
 * @Date 2024/8/4
 */
@Tag(name = "会话列表API")
@RestController
@RequestMapping("/chat/contact")
public class ContactController {

    @Resource
    private ContactService contactService;

    /**
     * 获取会话列表
     *
     * @return
     */
    @Operation(summary = "获取会话列表")
    @GetMapping("/page")
    public CommonResult<PageResult<ContactRoomRespVO>> getContactPage(@Valid ContactPageReqVO contactPage) {
        return CommonResult.success(contactService.getContactPage(contactPage, SecurityFrameworkUtils.getLoginUserId()));
    }

    /**
     * 获取会话详情
     *
     * @param roomId roomId
     */
    @Operation(summary = "获取会话详情")
    @GetMapping("/detail")
    public CommonResult<ContactRoomRespVO> getContactDetail(@RequestParam("roomId") Long roomId) {
        return CommonResult.success(contactService.getContactDetail(SecurityFrameworkUtils.getLoginUserId(), roomId));
    }

    /**
     * 获取会话详情(联系人列表发消息)
     *
     * @param friendId 好友id
     * @return
     */
    @Operation(summary = "获取会话详情(联系人列表发消息)")
    @GetMapping("/detail/friend")
    public CommonResult<ContactRoomRespVO> getContactDetailByFriend(@RequestParam("friendId") Long friendId) {
        return CommonResult.success(contactService.getContactDetailByFriend(SecurityFrameworkUtils.getLoginUserId(), friendId));
    }

}
