package com.koi.system.service.websiteConfig.impl;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import com.koi.common.utils.http.IpUtils;
import com.koi.framework.redis.core.utils.RedisUtils;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.system.domain.websiteConfig.entity.LogVisit;
import com.koi.system.mapper.mysql.websiteConfig.LogVisitMapper;
import com.koi.system.service.websiteConfig.LogVisitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.koi.system.constants.RedisKeyConstants.UNIQUE_VISITOR;
import static com.koi.system.constants.RedisKeyConstants.UNKNOWN;

/**
 * 访问日志 service 实现类
 *
 * @Author zjl
 * @Date 2024/1/29 17:30
 */
@Service
public class LogVisitServiceImpl implements LogVisitService {

    @Resource
    private LogVisitMapper logVisitMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void report() {
        HttpServletRequest request = IpUtils.getRequest();
        if (request == null) {
            return;
        }
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OS os = userAgent.getOs();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + os.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());

        // 判断是否访问
        if (!RedisUtils.sHasKey(UNIQUE_VISITOR, md5)) {
            String ipSource = IpUtils.getIpSource(ipAddress);
            LogVisit logVisit = LogVisit.builder()
                    .userId(SecurityFrameworkUtils.getLoginUserId())
                    .ip(ipAddress)
                    .ipSource(StringUtils.isBlank(ipSource) ? UNKNOWN : ipSource)
                    .os(os.getName())
                    .browser(browser.getName())
                    .build();
            logVisitMapper.insert(logVisit);
            // 保存唯一标识
            RedisUtils.sSet(UNIQUE_VISITOR, md5);
        }
    }
}
