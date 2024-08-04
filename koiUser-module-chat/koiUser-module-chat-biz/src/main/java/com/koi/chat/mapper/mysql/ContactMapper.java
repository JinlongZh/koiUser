package com.koi.chat.mapper.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.chat.domain.entity.ContactDO;
import com.koi.chat.domain.vo.request.ContactPageReqVO;
import com.koi.framework.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 联系人映射器
 *
 * @author zjl
 * @date 2023/10/10
 */
public interface ContactMapper extends BaseMapper<ContactDO> {

    void refreshOrCreateActiveTime(@Param("roomId") Long roomId, @Param("memberUidList") List<Long> memberUidList, @Param("msgId") Long msgId, @Param("activeTime") LocalDateTime activeTime);

    default Page<ContactDO> getContactPage(ContactPageReqVO contactPage, Long userId) {
        Page<ContactDO> page = MyBatisUtils.buildPage(contactPage);
        return selectPage(page, new LambdaQueryWrapper<ContactDO>()
                .eq(ContactDO::getUserId, userId)
                .orderByDesc(ContactDO::getActiveTime)
        );
    }

    default List<ContactDO> getByRoomIds(List<Long> roomIdList, Long userId) {
        return selectList(new LambdaQueryWrapper<ContactDO>()
                .in(ContactDO::getRoomId, roomIdList)
                .eq(ContactDO::getUserId, userId));
    }

    default ContactDO getByRoomId(Long roomId, Long loginUserId) {
        return selectOne(new LambdaQueryWrapper<ContactDO>()
                .eq(ContactDO::getRoomId, roomId)
                .eq(ContactDO::getUserId, loginUserId));
    }
}




