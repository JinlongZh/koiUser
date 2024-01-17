package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.blog.domain.entity.Talk;
import com.koi.blog.domain.vo.request.*;
import com.koi.blog.domain.vo.response.TalkAdminRespVO;
import com.koi.blog.domain.vo.response.TalkRespVO;
import com.koi.blog.mapper.mysql.TalkMapper;
import com.koi.blog.service.TalkService;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.json.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zjl
 * @date 2023/12/16
 */
@Service
public class TalkServiceImpl implements TalkService {

    @Resource
    private TalkMapper talkMapper;

    @Override
    public TalkRespVO getTalkDetail(Long id) {
        Talk talk = talkMapper.selectById(id);
        TalkRespVO talkRespVO = BeanCopyUtils.copyObject(talk, TalkRespVO.class);
        talkPictureConvert(talkRespVO);
        return talkRespVO;
    }

    @Override
    public List<TalkRespVO> getTalkDetailByIdList(List<Long> idList) {
        List<Talk> talkList = talkMapper.selectBatchIds(idList);
        List<TalkRespVO> talkRespVOList = BeanCopyUtils.copyList(talkList, TalkRespVO.class);
        for (TalkRespVO item : talkRespVOList) {
            talkPictureConvert(item);
        }

        return talkRespVOList;
    }

    @Override
    public PageResult<TalkRespVO> pageTalk(TalkPageQueryReqVO req) {
        Page<Talk> talkPage = talkMapper.pageTalk(req);
        List<TalkRespVO> talkRespVOList = BeanCopyUtils.copyList(talkPage.getRecords(), TalkRespVO.class);
        for (TalkRespVO item : talkRespVOList) {
            talkPictureConvert(item);
        }
        return new PageResult<>(talkRespVOList, talkPage.getTotal());
    }

    @Override
    public PageResult<TalkAdminRespVO> pageTalkAdmin(TalkAdminQueryReqVO req) {
        Page<Talk> talkPage = talkMapper.selectTalkAdminPage(req);
        List<TalkAdminRespVO> talkAdminRespVOList = BeanCopyUtils.copyList(talkPage.getRecords(), TalkAdminRespVO.class);
        for (TalkAdminRespVO item : talkAdminRespVOList) {
            // 转化说说图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImageList(CollectionUtils.castList(JsonUtils.parseObject(item.getImages(), List.class), String.class));
            }
        }
        return new PageResult<>(talkAdminRespVOList, talkPage.getTotal());
    }

    @Override
    public void addTalk(TalkAdminAddReqVO req) {
        Talk talk = Talk.builder()
                .content(req.getContent())
                .images(req.getImages())
                .talkTop(req.getTalkTop())
                .status(req.getStatus())
                .viewCount(0)
                .build();
        talkMapper.insert(talk);
    }

    @Override
    public void updateTalk(TalkAdminUpdateReqVO req) {
        Talk talk = Talk.builder()
                .id(req.getId())
                .content(req.getContent())
                .images(req.getImages())
                .talkTop(req.getTalkTop())
                .status(req.getStatus())
                .build();
        talkMapper.updateById(talk);
    }

    @Override
    public void deleteTalk(Long talkId) {
        talkMapper.deleteById(talkId);
    }

    @Override
    public void updateTalkTop(TalkTopReqVO req) {
        Talk talk = Talk.builder()
                .id(req.getId())
                .talkTop(req.getTop())
                .build();
        talkMapper.updateById(talk);
    }

    @Override
    public Talk getTalkById(Long talkId) {
        return talkMapper.selectById(talkId);
    }

    /**
     * 说说转换图片格式
     *
     * @param talkRespVO
     */
    private void talkPictureConvert(TalkRespVO talkRespVO) {
        if (Objects.nonNull(talkRespVO.getImages())) {
            talkRespVO.setImageList(CollectionUtils.castList(JsonUtils.parseObject(talkRespVO.getImages(), List.class), String.class));
        }
    }
}




