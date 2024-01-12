package com.koi.blog.service;

import com.koi.blog.domain.entity.Category;
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

    List<OptionRespVO> getCategoryOption();

    Category getCategoryById(Long id);
}
