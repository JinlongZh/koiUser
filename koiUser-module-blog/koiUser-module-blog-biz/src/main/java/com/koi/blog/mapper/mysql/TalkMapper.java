package com.koi.blog.mapper.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.blog.domain.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.blog.domain.vo.request.TalkAdminQueryReqVO;
import com.koi.blog.domain.vo.request.TalkPageQueryReqVO;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.framework.mybatis.utils.MyBatisUtils;

import static com.koi.common.enums.CommonStatusEnum.ENABLE;

public interface TalkMapper extends BaseMapper<Talk> {

    default Page<Talk> pageTalk(TalkPageQueryReqVO req) {
        Page<Talk> page = MyBatisUtils.buildPage(req);
        return selectPage(page, new LambdaQueryWrapperX<Talk>()
                .eq(Talk::getStatus, ENABLE)
                .orderByDesc(Talk::getTalkTop)
                .orderByDesc(Talk::getId));
    }

    default Page<Talk> selectTalkAdminPage(TalkAdminQueryReqVO req) {
        Page<Talk> page = MyBatisUtils.buildPage(req);
        return selectPage(page, new LambdaQueryWrapperX<Talk>()
                .eqIfPresent(Talk::getStatus, req.getStatus())
        );
    }
}




