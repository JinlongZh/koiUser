package com.koi.framework.jackson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.koi.common.utils.json.JsonUtils;
import com.koi.framework.jackson.core.databind.LocalDateTimeDeserializer;
import com.koi.framework.jackson.core.databind.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

/**
 * jackson 自动配置类
 *
 * @Author zjl
 * @Date 2023/9/5 15:34
 */
@Slf4j
@AutoConfiguration
public class JacksonAutoConfiguration {


    @Bean
    public BeanPostProcessor objectMapperBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (!(bean instanceof ObjectMapper)) {
                    return bean;
                }
                ObjectMapper objectMapper = (ObjectMapper) bean;
                SimpleModule simpleModule = new SimpleModule();

                simpleModule
                        .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
                        .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);

                objectMapper.registerModules(simpleModule);

                JsonUtils.init(objectMapper);
                log.info("初始化 jackson 自动配置");
                return bean;
            }
        };
    }

}
