package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.koi.blog.domain.entity.Article;
import com.koi.blog.domain.entity.Category;
import com.koi.blog.domain.vo.request.CategoryAddReqVO;
import com.koi.blog.domain.vo.request.CategoryAdminQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryUpdateReqVO;
import com.koi.blog.domain.vo.response.CategoryAdminRespVO;
import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.blog.mapper.mysql.ArticleMapper;
import com.koi.blog.mapper.mysql.CategoryMapper;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.OptionRespVO;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.framework.mybatis.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * @author zjl
 * @date 2023/12/16
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageResult<CategoryRespVO> listCategories() {
        return new PageResult<>(categoryMapper.getCategories(),
                categoryMapper.selectCount(null));
    }

    @Override
    public List<OptionRespVO> getCategoryOption(CategoryQueryReqVO req) {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapperX<Category>()
                .likeIfPresent(Category::getCategoryName, req.getKeywords()));
        return categoryList.stream()
                .map(item -> {
                    OptionRespVO optionRespVO = new OptionRespVO();
                    optionRespVO.setId(item.getId());
                    optionRespVO.setName(item.getCategoryName());
                    return optionRespVO;
                }).collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public PageResult<CategoryAdminRespVO> getCategoryAdminPage(CategoryAdminQueryReqVO req) {
        // 查询分类数量
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(req.getKeywords()), Category::getCategoryName, req.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类信息
        List<CategoryAdminRespVO> categoryAdminRespVOList = categoryMapper.getCategoryAdminPage(req, PageUtils.getStart(req), req.getPageSize());
        return new PageResult<>(categoryAdminRespVOList, count);
    }

    @Override
    public void addCategory(CategoryAddReqVO req) {
        Category category = new Category();
        category.setCategoryName(req.getCategoryName());

        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(CategoryUpdateReqVO req) {
        Category category = new Category();
        category.setId(req.getId());
        category.setCategoryName(req.getCategoryName());

        categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        // 检查有没有文章
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getCategoryId, categoryId
                ));
        if (count > 0) {
            throw new ServiceException(BAD_REQUEST.getCode(), "分类下存在文章，无法删除");
        }

        categoryMapper.deleteById(categoryId);
    }
}




