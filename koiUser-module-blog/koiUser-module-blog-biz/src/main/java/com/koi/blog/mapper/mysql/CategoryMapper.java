package com.koi.blog.mapper.mysql;

import com.koi.blog.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.blog.domain.vo.response.CategoryRespVO;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryRespVO> getCategories();
}




