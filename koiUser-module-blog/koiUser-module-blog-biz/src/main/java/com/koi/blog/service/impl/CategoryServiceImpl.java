package com.koi.blog.service.impl;

import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.blog.mapper.mysql.CategoryMapper;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjl
 * @date 2023/12/16
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageResult<CategoryRespVO> listCategories() {
        return new PageResult<>(categoryMapper.getCategories(),
                categoryMapper.selectCount(null));
    }
}




