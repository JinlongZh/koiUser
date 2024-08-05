package com.koi.chat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.chat.domain.dto.RoomBaseInfoDTO;
import com.koi.chat.domain.entity.ContactDO;
import com.koi.chat.domain.entity.MessageDO;
import com.koi.chat.domain.entity.RoomDO;
import com.koi.chat.domain.entity.RoomFriendDO;
import com.koi.chat.domain.vo.request.ContactPageReqVO;
import com.koi.chat.domain.vo.response.ContactRoomRespVO;
import com.koi.chat.enums.RoomTypeEnum;
import com.koi.chat.mapper.mysql.ContactMapper;
import com.koi.chat.mapper.mysql.MessageMapper;
import com.koi.chat.mapper.mysql.RoomFriendMapper;
import com.koi.chat.mapper.mysql.RoomMapper;
import com.koi.chat.service.ContactService;
import com.koi.chat.service.RoomFriendService;
import com.koi.chat.strategy.msg.AbstractMessageHandler;
import com.koi.chat.strategy.msg.factory.MessageHandlerFactory;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;
import com.koi.system.api.user.UserInfoApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.koi.chat.convert.RoomConvert.getFriendUserId;
import static com.koi.chat.convert.RoomConvert.getFriendUserIdSet;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;


/**
 * 会话列表服务实现类
 *
 * @Author zjl
 * @Date 2023/10/13 18:03
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Resource
    private ContactMapper contactMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private RoomFriendService roomFriendService;
    @Resource
    private RoomFriendMapper roomFriendMapper;
    @Resource
    private UserInfoApi userInfoApi;
    @Resource
    private MessageMapper messageMapper;

    @Override
    public void refreshOrCreateActiveTime(Long id, List<Long> memberUserIdList, Long messageId, LocalDateTime createTime) {
        contactMapper.refreshOrCreateActiveTime(id, memberUserIdList, messageId, createTime);
    }

    @Override
    public PageResult<ContactRoomRespVO> getContactPage(ContactPageReqVO contactPage, Long userId) {
        Page<ContactDO> contactList = contactMapper.getContactPage(contactPage, userId);
        if (contactList.getTotal() == 0) {
            return new PageResult<>();
        }
        // 最后组装会话信息（名称，头像，未读数等）
        List<Long> roomIdList = contactList.getRecords().stream()
                .map(ContactDO::getRoomId)
                .collect(Collectors.toList());
        List<ContactRoomRespVO> contactRoomRespVOList = buildContactResp(userId, roomIdList);
        return new PageResult<>(contactRoomRespVOList, contactList.getTotal());
    }

    @Override
    public ContactRoomRespVO getContactDetail(Long userId, Long roomId) {
        RoomDO roomDO = roomMapper.selectById(roomId);
        if (Objects.isNull(roomDO)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "房间不存在");
        }
        return buildContactResp(userId, Collections.singletonList(roomId)).get(0);
    }

    @Override
    public ContactRoomRespVO getContactDetailByFriend(Long userId, Long friendId) {
        RoomFriendDO friendRoom = roomFriendService.getFriendRoom(userId, friendId);
        if (Objects.isNull(friendRoom)) {
            throw new ServiceException(BAD_REQUEST.getCode(), "房间不存在");
        }
        return buildContactResp(userId, Collections.singletonList(friendRoom.getRoomId())).get(0);
    }

    /**
     * 组装会话信息（名称，头像，未读数等）
     */
    private List<ContactRoomRespVO> buildContactResp(Long userId, List<Long> roomIdList) {
        Map<Long, RoomBaseInfoDTO> roomBaseInfoMap = getRoomBaseInfoMap(userId, roomIdList);
        // 最后一条消息
        List<Long> messageId = roomBaseInfoMap.values().stream()
                .map(RoomBaseInfoDTO::getLastMessageId)
                .collect(Collectors.toList());
        List<MessageDO> messageDOList = messageMapper.selectBatchIds(messageId);
        Map<Long, MessageDO> messageMap = messageDOList.stream().
                collect(Collectors.toMap(MessageDO::getId, Function.identity()));

        // 用户信息
        Set<Long> userIdList = messageDOList.stream().map(MessageDO::getFromUserId).collect(Collectors.toSet());
        Map<Long, UserInfoRespDTO> userInfoMap = userInfoApi.getUserInfoByUserIds(userIdList);

        // 消息未读数
        Map<Long, Integer> unReadCountMap = getUnReadCountMap(userId, roomIdList);

        return roomBaseInfoMap.values().stream()
                .map(roomBaseInfoDTO -> {
                    ContactRoomRespVO resp = new ContactRoomRespVO();
                    RoomBaseInfoDTO roomBaseInfo = roomBaseInfoMap.get(roomBaseInfoDTO.getRoomId());
                    resp.setAvatar(roomBaseInfo.getAvatar());
                    resp.setRoomId(roomBaseInfoDTO.getRoomId());
                    resp.setActiveTime(roomBaseInfoDTO.getActiveTime());
                    resp.setType(roomBaseInfo.getType());
                    resp.setName(roomBaseInfo.getName());

                    MessageDO messageDO = messageMap.get(roomBaseInfoDTO.getLastMessageId());
                    if (Objects.nonNull(messageDO)) {
                        AbstractMessageHandler abstractMessageHandler = MessageHandlerFactory.getStrategyNoNull(messageDO.getType());
                        String lastMessageNickName = userInfoMap.get(messageDO.getFromUserId()).getNickname();
                        resp.setLastMessageNickName(lastMessageNickName);
                        // 群聊加上 “ 用户名称：”
                        if (Objects.equals(roomBaseInfoDTO.getType(), RoomTypeEnum.GROUP.getType())) {
                            resp.setText(lastMessageNickName + ": " + abstractMessageHandler.showContactMessage(messageDO));
                        }
                        // 单聊不加
                        else if (Objects.equals(roomBaseInfoDTO.getType(), RoomTypeEnum.FRIEND.getType())) {
                            resp.setText(abstractMessageHandler.showContactMessage(messageDO));
                        }
                    }
                    resp.setUnreadCount(unReadCountMap.getOrDefault(roomBaseInfoDTO.getRoomId(), 0));
                    return resp;
                }).sorted(Comparator.comparing(ContactRoomRespVO::getActiveTime).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 获取消息未读数
     */
    private Map<Long, Integer> getUnReadCountMap(Long userId, List<Long> roomIdList) {
        if (Objects.isNull(userId)) {
            return new HashMap<>();
        }
        List<ContactDO> contactDOS = contactMapper.getByRoomIds(roomIdList, userId);
        return contactDOS.parallelStream()
                .map(contactDO -> Pair.of(contactDO.getRoomId(), messageMapper.getUnReadCount(contactDO.getRoomId(), contactDO.getReadTime(), userId)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    /**
     * 获取房间基本信息
     *
     * @param userId     用户id
     * @param roomIdList 房间id列表
     * @return
     */
    private Map<Long, RoomBaseInfoDTO> getRoomBaseInfoMap(Long userId, List<Long> roomIdList) {
        List<RoomDO> roomDOList = roomMapper.selectBatchIds(roomIdList);
        Map<Integer, List<Long>> groupRoomIdMap = roomDOList.stream().collect(Collectors.groupingBy(RoomDO::getType,
                Collectors.mapping(RoomDO::getId, Collectors.toList())));
        // 获取好友信息
        List<Long> RoomIdListInFriend = groupRoomIdMap.get(RoomTypeEnum.FRIEND.getType());
        Map<Long, UserInfoRespDTO> friendRoomMap = getUserInfoByFriendRoomIdList(RoomIdListInFriend, userId);

        return roomDOList.stream()
                .map(room -> {
                    RoomBaseInfoDTO roomBaseInfo = new RoomBaseInfoDTO();
                    roomBaseInfo.setRoomId(room.getId());
                    roomBaseInfo.setType(room.getType());
                    roomBaseInfo.setLastMessageId(room.getLastMessageId());
                    roomBaseInfo.setActiveTime(room.getActiveTime());

                    if (RoomTypeEnum.of(room.getType()) == RoomTypeEnum.GROUP) {
                        // TODO 群聊
                    } else if (RoomTypeEnum.of(room.getType()) == RoomTypeEnum.FRIEND) {
                        UserInfoRespDTO userInfo = friendRoomMap.get(room.getId());
                        roomBaseInfo.setName(userInfo.getNickname());
                        roomBaseInfo.setAvatar(userInfo.getAvatar());
                    }
                    return roomBaseInfo;
                })
                .collect(Collectors.toMap(RoomBaseInfoDTO::getRoomId, Function.identity()));
    }

    /**
     * 根据单聊房间号列表获取用户信息
     *
     * @param RoomIdListInFriend
     * @param userId
     * @Return Map<Long, UserInfoRespDTO>
     */
    private Map<Long, UserInfoRespDTO> getUserInfoByFriendRoomIdList(List<Long> RoomIdListInFriend, Long userId) {
        if (CollectionUtil.isEmpty(RoomIdListInFriend)) {
            return new HashMap<>();
        }
        List<RoomFriendDO> roomFriendDOList = roomFriendService.getByRoomIdList(RoomIdListInFriend);
        Set<Long> friendUserIdSet = getFriendUserIdSet(roomFriendDOList, userId);
        // 远程调用获取用户信息map
        Map<Long, UserInfoRespDTO> userInfoMap = userInfoApi.getUserInfoByUserIds(friendUserIdSet);
        return roomFriendDOList.stream()
                .collect(Collectors.toMap(RoomFriendDO::getRoomId, roomFriend -> {
                    Long friendUserId = getFriendUserId(roomFriend, userId);
                    UserInfoRespDTO userInfo = userInfoMap.get(friendUserId);
                    if (userInfo == null) {
                        userInfo = UserInfoRespDTO.builder()
                                .nickname("用户不存在")
                                .avatar("用户不存在")
                                .build();
                    }
                    return userInfo;
                }));
    }

}
