package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.koi.blog.domain.entity.Category;
import com.koi.blog.domain.vo.response.CategoryRespVO;
import com.koi.blog.mapper.mysql.CategoryMapper;
import com.koi.blog.service.CategoryService;
import com.koi.common.domain.OptionRespVO;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<OptionRespVO> getCategoryOption() {
        List<Category> categoryList = categoryMapper.selectList(null);
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
}




