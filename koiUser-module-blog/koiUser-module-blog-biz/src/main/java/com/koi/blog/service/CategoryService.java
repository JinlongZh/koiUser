package com.koi.blog.service;

import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.common.domain.PageResult;

/**
 * @author zjl
 * @date 2023/12/16
 */
public interface CategoryService {

    PageResult<CategoryRespVO> listCategories();
}
