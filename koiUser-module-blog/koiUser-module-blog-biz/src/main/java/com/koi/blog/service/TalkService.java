package com.koi.blog.service;

import com.koi.blog.domain.entity.Talk;
import com.koi.blog.domain.vo.request.*;
import com.koi.blog.domain.vo.response.TalkAdminRespVO;
import com.koi.blog.domain.vo.response.TalkRespVO;
import com.koi.common.domain.PageResult;

import java.util.List;

/**
 * @author zjl
 * @date 2023/12/16
 */
public interface TalkService {

    /**
     * 获取说说详情
     *
     * @param id
     * @return {@link TalkRespVO}
     */
    TalkRespVO getTalkDetail(Long id);

    /**
     * 根据id列表获取说说详情
     *
     * @param idList
     * @return {@link List}<{@link TalkRespVO}>
     */
    List<TalkRespVO> getTalkDetailByIdList(List<Long> idList);

    /**
     * 分页请求说说
     *
     * @param req
     * @return {@link PageResult}<{@link TalkRespVO}>
     */
    PageResult<TalkRespVO> pageTalk(TalkPageQueryReqVO req);

    PageResult<TalkAdminRespVO> pageTalkAdmin(TalkAdminQueryReqVO req);

    void addTalk(TalkAdminAddReqVO req);

    void updateTalk(TalkAdminUpdateReqVO req);

    void deleteTalk(Long talkId);

    void updateTalkTop(TalkTopReqVO req);

    Talk getTalkById(Long talkId);
}
