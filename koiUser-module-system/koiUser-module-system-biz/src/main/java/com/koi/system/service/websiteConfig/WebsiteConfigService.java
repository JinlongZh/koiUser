package com.koi.system.service.websiteConfig;

import com.koi.common.domain.PageResult;
import com.koi.system.domain.websiteConfig.entity.WebsiteConfig;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigAddReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigPageReqVO;
import com.koi.system.domain.websiteConfig.vo.request.WebsiteConfigUpdateReqVO;
import com.koi.system.domain.websiteConfig.vo.response.WebsiteConfigRespVO;

import java.util.Map;

/**
 * -
 *
 * @Author zjl
 * @Date 2024/1/24 20:11
 */
public interface WebsiteConfigService {
    
    void addConfig(WebsiteConfigAddReqVO req);

    void updateConfig(WebsiteConfigUpdateReqVO req);

    WebsiteConfig getWebsiteConfigById(Long id);

    PageResult<WebsiteConfigRespVO> getConfigPage(WebsiteConfigPageReqVO req);

    Map<String, String> getWebsiteConfig();
}
