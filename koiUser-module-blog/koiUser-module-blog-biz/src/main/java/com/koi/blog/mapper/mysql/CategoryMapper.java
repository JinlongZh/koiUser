package com.koi.blog.mapper.mysql;

import com.koi.blog.domain.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.blog.domain.vo.request.CategoryAdminQueryReqVO;
import com.koi.blog.domain.vo.response.CategoryAdminRespVO;
import com.koi.blog.domain.vo.response.CategoryRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryRespVO> getCategories();

    List<CategoryAdminRespVO> getCategoryAdminPage(@Param("req") CategoryAdminQueryReqVO req, @Param("current")Integer current, @Param("pageSize")Integer pageSize);
}




