package com.koi.chat.service;

import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.vo.request.MessageReqVO;
import com.koi.chat.domain.vo.request.MessagePageReqVO;
import com.koi.chat.domain.vo.request.ReadMessageReqVO;
import com.koi.chat.domain.vo.response.MessageRespVO;
import com.koi.common.domain.PageResult;

/**
 * 聊天 service
 *
 * @Author zjl
 * @Date 2023/10/8 22:30
 */
public interface ChatService {

    /**
     * 发送消息
     *
     * @param messageReqVO 聊天信息请求
     * @param loginUserId      登录用户id
     * @return
     */
    Long sendMsg(MessageReqVO messageReqVO, Long loginUserId);

    /**
     * 根据消息获取消息前端展示的消息
     *
     * @param chatMessageDO 聊天信息
     * @param receiveUserId  接收uid
     * @return 聊天消息响应
     */
    MessageRespVO getMessageResp(MessageDO chatMessageDO, Long receiveUserId);

    /**
     * 根据消息id获取消息前端展示的消息
     *
     * @param msgId      消息id
     * @param receiveUserId 接收uid
     * @return 聊天信息response vo
     */
    MessageRespVO getMessageResp(Long msgId, Long receiveUserId);

    /**
     * 获取消息列表
     *
     * @param userId      登录用户id
     * @param messagePageReqVO 消息页面req-vo
     * @return 页面结果＜聊天消息resp vo＞
     */
    PageResult<MessageRespVO> getMessagePage(Long userId, MessagePageReqVO messagePageReqVO);

    /**
     * 消息阅读上报
     *
     * @param loginUserId
     * @param readMessageReqVO
     */
    void readMessage(Long loginUserId, ReadMessageReqVO readMessageReqVO);
}
