package com.koi.framework.limitrate.core.aspect;

import com.koi.common.exception.ServiceException;
import com.koi.framework.limitrate.core.annotation.LimitRate;
import com.koi.framework.limitrate.core.service.LimitTime;
import com.koi.framework.limitrate.core.utils.LimitRateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.TOO_MANY_REQUESTS;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 12:02
 */
@Aspect
@Order(999)
@RequiredArgsConstructor
public class LimitRateCheckAspect {

    private static final Logger log = LoggerFactory.getLogger(LimitRateCheckAspect.class);

    private final LimitRateUtil limitRateUtil;

    private final HttpServletRequest request;

    /**
     * @param point
     */

    @Around("@annotation(com.koi.framework.limitrate.core.annotation.LimitRate)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.trace("enter LimitRateCheckAop");
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解中的内容
        LimitRate[] limitRates = methodSignature.getMethod().getAnnotationsByType(LimitRate.class);
        Arrays.sort(limitRates, Comparator.comparing(LimitRate::order).reversed());

        Map<String, LimitRate> rollbackKey = new HashMap<>();

        for (LimitRate limitRate : limitRates) {

            String key = buildRedisKey(point, methodSignature, limitRate);
            String keySuffix = limitRateUtil.getKeySuffix(limitRate.type(), request);
            key = keySuffix + key;
            long ts = System.currentTimeMillis();
            // 计算时间单位
            LimitTime time = LimitTime.calculateTime(ts, limitRate.interval(), limitRate.unit());
            Long count = limitRateUtil.consumeCount(key, 1L, limitRate.rate(), limitRate.type(), limitRate.capacity(), time);

            // 判断发生异常时是否需要放回令牌
            if (limitRate.rbEx()) {
                if (count < 0) {
                    // 抛出异常
                    throw new ServiceException(TOO_MANY_REQUESTS.getCode(), limitRate.msg());
                } else {
                    // 记录要回滚的key
                    rollbackKey.put(key, limitRate);
                }

            } else {
                if (count < 0) {
                    // 抛出异常
                    throw new ServiceException(TOO_MANY_REQUESTS.getCode(), limitRate.msg());
                }
            }
        }
        try {
            return point.proceed();
        } catch (Exception e) {
            // 检查是否需要回滚的key
            if (CollectionUtils.isEmpty(rollbackKey)) {
                doRollback(rollbackKey);
            }
            throw e;
        }

    }

    /**
     * 回滚key
     *
     * @param rollbackKey
     */
    private void doRollback(Map<String, LimitRate> rollbackKey) {
        for (Map.Entry<String, LimitRate> entry : rollbackKey.entrySet()) {
            // 增加次数
            limitRateUtil.addCount(entry.getKey(), 1L, entry.getValue().capacity(), entry.getValue().type());
        }
    }

    /**
     * 构建key
     *
     * @param point
     * @param methodSignature
     * @param limitRate
     * @return
     */
    private String buildRedisKey(ProceedingJoinPoint point, MethodSignature methodSignature, LimitRate limitRate) {
        // 方法名
        String methodName = methodSignature.getName();
        // 类名
        String className = methodSignature.getDeclaringTypeName();
        // 目标类、方法
        log.debug("类名{}方法名{}", className, methodName);
        String keyPrefix;
        // 如果没有指定key前缀,则使用类名+方法名的hash值
        if (StringUtils.isEmpty(limitRate.name())) {
            // 防止内容过长,只取其hash值作为key的部分
            keyPrefix = String.valueOf(Math.abs(MessageFormat.format("{0}:{1}", className, methodName).hashCode()));
        } else {
            keyPrefix = limitRate.name();
        }

        String paramKey = "";
        Object[] args = point.getArgs();
        if (!StringUtils.isEmpty(limitRate.key())) {
            //构建EL表达式的key
            paramKey = buildExpressKey(methodSignature, limitRate.key(), args);
        }
        return keyPrefix + paramKey;
    }

    /**
     * 使用el表达式解析key
     *
     * @param methodSignature
     * @param el
     * @param args
     * @return
     */
    private String buildExpressKey(MethodSignature methodSignature, String el, Object[] args) {
        String paramKey = "";

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(el);
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        try {
            // 解析el表达式，将#id等替换为参数值
            paramKey = expression.getValue(context).toString();
            log.debug("限流工具构建key,el表达式解析key成功,el={},解析后值为:{}", el, paramKey);
        } catch (Exception e) {
            log.error("参数key={}解析失败,{}{}", el, e, e.getMessage());
        }
        return paramKey;
    }

}
