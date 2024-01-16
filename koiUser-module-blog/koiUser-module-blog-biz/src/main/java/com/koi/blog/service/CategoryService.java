package com.koi.blog.service;

import com.koi.blog.domain.entity.Category;
import com.koi.blog.domain.vo.request.CategoryAddReqVO;
import com.koi.blog.domain.vo.request.CategoryAdminQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryQueryReqVO;
import com.koi.blog.domain.vo.request.CategoryUpdateReqVO;
import com.koi.blog.domain.vo.response.CategoryAdminRespVO;
import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.common.domain.OptionRespVO;
import com.koi.common.domain.PageResult;

import java.util.List;

/**
 * @author zjl
 * @date 2023/12/16
 */
public interface CategoryService {

    PageResult<CategoryRespVO> listCategories();

    List<OptionRespVO> getCategoryOption(CategoryQueryReqVO req);

    Category getCategoryById(Long id);

    PageResult<CategoryAdminRespVO> getCategoryAdminPage(CategoryAdminQueryReqVO req);

    void addCategory(CategoryAddReqVO req);

    void updateCategory(CategoryUpdateReqVO req);

    void deleteCategory(Long categoryId);
}
