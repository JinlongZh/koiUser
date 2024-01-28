package com.koi.system.service.websiteConfig.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.common.domain.PageResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.domain.websiteConfig.entity.WebsiteConfig;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigAddReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigPageReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigUpdateReqVO;
import com.koi.system.domain.websiteConfig.vo.response.WebsiteConfigRespVO;
import com.koi.system.mapper.mysql.websiteConfig.WebsiteConfigMapper;
import com.koi.system.service.websiteConfig.WebsiteConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/24 20:11
 */
@Service
public class WebsiteConfigServiceImpl implements WebsiteConfigService {

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;

    @Override
    public void addConfig(WebsiteConfigAddReqVO req) {
        // 检查是否存在重复
        WebsiteConfig alreadyConfig = websiteConfigMapper.selectOne(new LambdaQueryWrapper<WebsiteConfig>()
                .eq(WebsiteConfig::getConfigKey, req.getConfigKey()));
        if (alreadyConfig != null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "已存在该配置");
        }

        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .configName(req.getConfigName())
                .configKey(req.getConfigKey())
                .configValue(req.getConfigValue())
                .configType(req.getConfigType())
                .build();

        websiteConfigMapper.insert(websiteConfig);
    }

    @Override
    public void updateConfig(WebsiteConfigUpdateReqVO req) {
        // 检查是否存在
        WebsiteConfig alreadyConfig = websiteConfigMapper.selectById(req.getId());
        if (alreadyConfig == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "不存在该配置");
        }

        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(req.getId())
                .configName(req.getConfigName())
                .configKey(req.getConfigKey())
                .configValue(req.getConfigValue())
                .configType(req.getConfigType())
                .build();

        websiteConfigMapper.updateById(websiteConfig);
    }

    @Override
    public WebsiteConfig getWebsiteConfigById(Long id) {
        return websiteConfigMapper.selectById(id);
    }

    @Override
    public PageResult<WebsiteConfigRespVO> getConfigPage(WebsiteConfigPageReqVO req) {
        Page<WebsiteConfig> websiteConfigPage = websiteConfigMapper.selectConfigPage(req);

        List<WebsiteConfigRespVO> websiteConfigRespVOList = BeanCopyUtils.copyList(websiteConfigPage.getRecords(), WebsiteConfigRespVO.class);

        return new PageResult<>(websiteConfigRespVOList,  websiteConfigPage.getTotal());
    }

    @Override
    public Map<String, String> getWebsiteConfig() {
        List<WebsiteConfig> websiteConfigList = websiteConfigMapper.getWebsiteConfig();

        Map<String, String> configMap = new HashMap<>();
        for (WebsiteConfig config : websiteConfigList) {
            configMap.put(config.getConfigKey(), config.getConfigValue());
        }
        return configMap;
    }
}
