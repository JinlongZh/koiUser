package com.koi.blog.service;

import com.koi.blog.domain.entity.Article;
import com.koi.blog.domain.vo.request.*;
import com.koi.blog.domain.vo.response.ArticleAdminRespVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.common.domain.PageResult;

import java.util.List;

/**
 * @Author zjl
 * @Date 2023/12/16 21:42
 */
public interface ArticleService {

    /**
     * 查询首页文章分页
     *
     * @param reqVO
     * @return {@link PageResult}<{@link ArticleRespVO}>
     */
    PageResult<ArticleRespVO> pageHomeArticle(ArticlePageQueryReqVO reqVO);

    /**
     * 获取文章详情
     *
     * @param id
     * @return {@link ArticleRespVO}
     */
    ArticleRespVO getArticleDetail(Long id);

    Boolean addArticleViewCount(Long id);

    /**
     * 根据id列表获取文章详情
     *
     * @param idList
     * @return {@link List}<{@link ArticleRespVO}>
     */
    List<ArticleRespVO> getArticleDetailByIdList(List<Long> idList);

    PageResult<ArticleAdminRespVO> pageArticleAdmin(ArticleAdminQueryReqVO req);

    void addArticle(ArticleAdminAddReqVO req);

    void updateArticle(ArticleAdminUpdateReqVO req);

    void deleteArticle(Long articleId);

    void updateArticleTop(ArticleTopReqVO req);

    Article getArticleById(Long id);
}
