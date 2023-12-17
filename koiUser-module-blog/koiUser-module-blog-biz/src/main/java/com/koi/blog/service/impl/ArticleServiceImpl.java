package com.koi.blog.service.impl;

import cn.hutool.core.util.PageUtil;
import com.koi.blog.domain.entity.Article;
import com.koi.blog.domain.vo.request.ArticlePageQueryReqVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.blog.mapper.mysql.ArticleMapper;
import com.koi.blog.service.ArticleService;
import com.koi.common.domain.PageResult;
import com.koi.framework.mybatis.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zjl
 * @Date 2023/12/16 21:43
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageResult<ArticleRespVO> pageHomeArticle(ArticlePageQueryReqVO reqVO) {
        List<ArticleRespVO> articleRespVOList = articleMapper.pageArticle(reqVO, PageUtils.getStart(reqVO), reqVO.getPageSize());
        return new PageResult<>(articleRespVOList, articleMapper.getArticleNum(reqVO));
    }
}
