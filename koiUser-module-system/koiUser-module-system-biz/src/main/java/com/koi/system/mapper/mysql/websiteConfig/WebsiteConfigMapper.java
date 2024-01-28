package com.koi.system.mapper.mysql.websiteConfig;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.koi.framework.mybatis.utils.MyBatisUtils;
import com.koi.system.domain.websiteConfig.entity.WebsiteConfig;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigPageReqVO;

import java.util.List;

public interface WebsiteConfigMapper extends BaseMapper<WebsiteConfig> {

    default Page<WebsiteConfig> selectConfigPage(WebsiteConfigPageReqVO req) {
        Page<WebsiteConfig> page = MyBatisUtils.buildPage(req);
        return selectPage(page, new LambdaQueryWrapperX<WebsiteConfig>()
                .eqIfPresent(WebsiteConfig::getConfigName, req.getConfigName())
                .eqIfPresent(WebsiteConfig::getConfigType, req.getConfigType())
                .orderByAsc(WebsiteConfig::getConfigType)
        );
    }

    default List<WebsiteConfig> getWebsiteConfig() {
        return selectList(new LambdaQueryWrapper<WebsiteConfig>()
                .eq(WebsiteConfig::getConfigType, 0));
    }
}




