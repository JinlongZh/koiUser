package com.koi.blog.service;

import com.koi.blog.domain.vo.response.TalkRespVO;

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

}
