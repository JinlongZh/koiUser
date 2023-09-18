package com.koi.framework.limitrate.config;

import com.koi.framework.limitrate.core.aspect.LimitRateCheckAspect;
import com.koi.framework.limitrate.core.service.LimitRateService;
import com.koi.framework.limitrate.core.service.LimitRateServiceImpl;
import com.koi.framework.limitrate.core.utils.LimitRateUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 限流自动配置
 *
 * @Author zjl
 * @Date 2023/9/18 12:50
 */
@AutoConfiguration
public class LimitRateAutoConfiguration {


    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public LimitRateService limitRateService() {
        return new LimitRateServiceImpl(redisTemplate);
    }

    @Bean
    public LimitRateUtil limitRateUtil(LimitRateService limitRateService) {
        return new LimitRateUtil(limitRateService);
    }

    @Bean
    public LimitRateCheckAspect limitRateCheckAspect(LimitRateUtil limitRateUtil) {
        return new LimitRateCheckAspect(limitRateUtil, request);
    }
}
