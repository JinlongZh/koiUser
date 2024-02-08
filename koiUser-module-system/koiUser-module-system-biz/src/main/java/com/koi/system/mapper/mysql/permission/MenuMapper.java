package com.koi.system.mapper.mysql.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.system.domain.permission.entity.Menu;
import com.koi.system.domain.permission.vo.request.MenuQueryReqVO;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    default List<Menu> listMenus(MenuQueryReqVO req) {
        return selectList(new LambdaQueryWrapperX<Menu>()
                .likeIfPresent(Menu::getName, req.getKeywords()));
    }
}




