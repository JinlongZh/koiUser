package com.koi.chat.controller.app;

import com.koi.chat.domain.vo.request.MessagePageReqVO;
import com.koi.chat.domain.vo.request.MessageReqVO;
import com.koi.chat.domain.vo.request.ReadMessageReqVO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.chat.service.ChatService;
import com.koi.common.domain.CommonResult;
import com.koi.common.domain.PageResult;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 聊天API
 *
 * @Author zjl
 * @Date 2024/8/4
 */
@Tag(name = "聊天API")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 发送消息
     *
     * @param chatMessageReqVO 聊天信息请求
     * @return
     */
    @Operation(summary = "发送消息")
    @PostMapping("/send")
    public CommonResult<MessageRespVO> sendMsg(@Valid @RequestBody MessageReqVO chatMessageReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Long messageId = chatService.sendMsg(chatMessageReqVO, loginUserId);

        MessageRespVO messageResp = chatService.getMessageResp(messageId, SecurityFrameworkUtils.getLoginUserId());
        return CommonResult.success(messageResp);
    }

    @Operation(summary = "消息列表")
    @GetMapping("/page")
    public CommonResult<PageResult<MessageRespVO>> getMessagePage(@Valid MessagePageReqVO messagePageReqVO) {
        PageResult<MessageRespVO> pageResult = chatService.getMessagePage(SecurityFrameworkUtils.getLoginUserId(), messagePageReqVO);
        return CommonResult.success(pageResult);
    }

    @Operation(summary = "消息阅读上报")
    @PutMapping("/read")
    public CommonResult<String> readMessage(@Valid @RequestBody ReadMessageReqVO readMessageReqVO) {
        chatService.readMessage(SecurityFrameworkUtils.getLoginUserId(), readMessageReqVO);
        return CommonResult.success("消息阅读上报成功");
    }

}
