package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.blog.constants.FriendLinkStatusEnum;
import com.koi.blog.domain.entity.FriendLink;
import com.koi.blog.domain.vo.request.FriendLinkAddReqVO;
import com.koi.blog.domain.vo.response.FriendLinkRespVO;
import com.koi.blog.mapper.mysql.FriendLinkMapper;
import com.koi.blog.service.FriendLinkService;
import com.koi.common.utils.bean.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 友链服务类 实现类
 *
 * @Author zjl
 * @Date 2024/7/29
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Override
    public List<FriendLinkRespVO> listFriendLinks() {
        List<FriendLink> friendLinkList = friendLinkMapper.selectList(new LambdaQueryWrapper<FriendLink>()
                .eq(FriendLink::getStatus, FriendLinkStatusEnum.PUBLIC.getStatus())
                .orderByDesc(FriendLink::getId)
        );
        return BeanCopyUtils.copyList(friendLinkList, FriendLinkRespVO.class);
    }

    @Override
    public void submitFriendLink(FriendLinkAddReqVO friendLinkAddReqVO) {
        FriendLink friendLink = BeanCopyUtils.copyObject(friendLinkAddReqVO, FriendLink.class);
        friendLink.setStatus(FriendLinkStatusEnum.AUDIT.getStatus());
        friendLinkMapper.insert(friendLink);
    }
}
