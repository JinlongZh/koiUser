package com.koi.chat.service;

import com.koi.chat.domain.vo.request.ContactPageReqVO;
import com.koi.chat.domain.vo.response.ContactRoomRespVO;
import com.koi.common.domain.PageResult;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话列表服务
 *
 * @Author zjl
 * @Date 2023/10/13 18:03
 */
public interface ContactService {

    /**
     * 更新所有人的会话时间，没有就直接插入
     *
     * @param id               id
     * @param memberUserIdList 成员uid列表
     * @param messageId        messageId
     * @param createTime       创建时间
     */
    void refreshOrCreateActiveTime(Long id, List<Long> memberUserIdList, Long messageId, LocalDateTime createTime);

    /**
     * 获取联系人页面
     *
     * @param contactPage
     * @param userId
     * @return
     */
    PageResult<ContactRoomRespVO> getContactPage(ContactPageReqVO contactPage, Long userId);

    /**
     * 获取会话详情
     *
     * @param userId
     * @param roomId
     * @Return ContactRoomRespVO
     */
    ContactRoomRespVO getContactDetail(Long userId, Long roomId);

    /**
     * 获取会话详情(联系人列表发消息用)
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return
     */
    ContactRoomRespVO getContactDetailByFriend(Long userId, Long friendId);
}
