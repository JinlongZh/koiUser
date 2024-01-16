package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.blog.domain.entity.Article;
import com.koi.blog.domain.entity.Category;
import com.koi.blog.domain.vo.request.*;
import com.koi.blog.domain.vo.response.ArticleAdminRespVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.blog.mapper.mysql.ArticleMapper;
import com.koi.blog.mapper.mysql.CategoryMapper;
import com.koi.blog.service.ArticleService;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.exception.enums.GlobalErrorCodeConstants;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.framework.mybatis.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.koi.blog.constants.ArticleStatusEnum.DRAFT;
import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * @Author zjl
 * @Date 2023/12/16 21:43
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageResult<ArticleRespVO> pageHomeArticle(ArticlePageQueryReqVO reqVO) {
        List<ArticleRespVO> articleRespVOList = articleMapper.pageArticle(reqVO, PageUtils.getStart(reqVO), reqVO.getPageSize());
        return new PageResult<>(articleRespVOList, articleMapper.getArticleNum(reqVO));
    }

    @Override
    public ArticleRespVO getArticleDetail(Long id) {
        Article article = articleMapper.selectById(id);
        Category category = categoryMapper.selectById(article.getCategoryId());

        ArticleRespVO articleRespVO = BeanCopyUtils.copyObject(article, ArticleRespVO.class);
        articleRespVO.setCategoryName(category.getCategoryName());
        return articleRespVO;
    }

    @Override
    public List<ArticleRespVO> getArticleDetailByIdList(List<Long> idList) {
        return articleMapper.getArticleDetailByIdList(idList);
    }

    @Override
    public PageResult<ArticleAdminRespVO> pageArticle(ArticleAdminQueryReqVO req) {
        Long count = articleMapper.countArticleAdmin(req);
        if (count == 0) {
            return new PageResult<>();
        }
        List<ArticleAdminRespVO> articleAdminRespVOList = articleMapper.pageArticleAdmin(req, PageUtils.getStart(req), req.getPageSize());
        return new PageResult<>(articleAdminRespVOList, count);
    }

    @Override
    public void addArticle(ArticleAdminAddReqVO req) {
        if (!Objects.equals(req.getArticleStatus(), DRAFT.getStatus())) {
            if (req.getCategoryId() == null) {
                throw new ServiceException(BAD_REQUEST.getCode(), "非草稿文章分类不能为空");
            }
            if (req.getArticleCover() == null || StringUtils.isBlank(req.getArticleCover())) {
                throw new ServiceException(BAD_REQUEST.getCode(), "非草稿文章封面不能为空");
            }
            vaildCategory(req.getCategoryId());
        }

        Article article = Article.builder()
                .articleTitle(req.getArticleTitle())
                .categoryId(req.getCategoryId())
                .articleCover(req.getArticleCover())
                .articleContent(req.getArticleContent())
                .articleTop(req.getArticleTop())
                .status(req.getArticleStatus() == null ? DRAFT.getStatus(): req.getArticleStatus())
                .viewCount(0)
                .build();
        articleMapper.insert(article);
    }

    @Override
    public void updateArticle(ArticleAdminUpdateReqVO req) {
        vaildCategory(req.getCategoryId());

        Article article = articleMapper.selectById(req.getId());
        if (article == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "文章不存在");
        }

        Article articleUpdate = Article.builder()
                .id(req.getId())
                .articleTitle(req.getArticleTitle())
                .categoryId(req.getCategoryId())
                .articleCover(req.getArticleCover())
                .articleContent(req.getArticleContent())
                .articleTop(req.getArticleTop())
                .status(req.getArticleStatus())
                .build();
        articleMapper.updateById(articleUpdate);

    }

    @Override
    public void deleteArticle(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "文章不存在");
        }
        articleMapper.deleteById(articleId);
    }

    @Override
    public void updateArticleTop(ArticleTopReqVO req) {
        Article article = articleMapper.selectById(req.getId());
        if (article == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "文章不存在");
        }
        Article articleUpdate = Article.builder()
                .id(req.getId())
                .articleTop(req.getTop())
                .build();
        articleMapper.updateById(articleUpdate);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleMapper.selectById(id);
    }

    /**
     * 验证文章分类
     *
     * @param categoryId
     */
    private void vaildCategory(Long categoryId) {
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getId, categoryId));
        if (category == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "分类不存在");
        }
    }
}
