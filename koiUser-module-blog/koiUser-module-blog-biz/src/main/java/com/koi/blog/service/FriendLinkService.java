package com.koi.blog.service;

import com.koi.blog.domain.vo.request.FriendLinkAddReqVO;
import com.koi.blog.domain.vo.response.FriendLinkRespVO;

import java.util.List;

/**
 * 友链服务类
 *
 * @Author zjl
 * @Date 2024/7/29
 */
public interface FriendLinkService {

    /**
     * 查询前台友链
     *
     * @param
     * @Return List<FriendLinkRespVO>
     */
    List<FriendLinkRespVO> listFriendLinks();

    /**
     * 用户提交友链
     *
     * @param friendLinkAddReqVO
     * @Return void
     */
    void submitFriendLink(FriendLinkAddReqVO friendLinkAddReqVO);
}
