package com.koi.blog.mapper.mysql;

import com.koi.blog.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.blog.domain.vo.request.ArticleAdminQueryReqVO;
import com.koi.blog.domain.vo.request.ArticlePageQueryReqVO;
import com.koi.blog.domain.vo.response.ArticleAdminRespVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.common.domain.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleRespVO> pageArticle(@Param("req") ArticlePageQueryReqVO req, @Param("current") Integer current, @Param("pageSize") Integer pageSize);

    Long getArticleNum(@Param("req") ArticlePageQueryReqVO req);

    List<ArticleRespVO> getArticleDetailByIdList(@Param("idList") List<Long> idList);

    List<ArticleAdminRespVO> pageArticleAdmin(@Param("req") ArticleAdminQueryReqVO req, @Param("current") Integer current, @Param("pageSize") Integer pageSize);

    Long countArticleAdmin(@Param("req") ArticleAdminQueryReqVO req);
}




