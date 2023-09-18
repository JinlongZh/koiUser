package com.koi.framework.limitrate.core.annotation;

import com.koi.framework.limitrate.core.enums.LimitTypeEnum;
import com.koi.framework.limitrate.core.enums.LimitUnitEnum;

import java.lang.annotation.*;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(LimitRates.class)
public @interface LimitRate {

    /**
     * redis key名称前缀,默认为类名+方法的hash key名作为缓存名称前缀
     * @return
     */
    String name() default "";

    /**
     *  key 支持EL表达式,可操作入参的对象
     * @return
     */
    String key() default "";

    /**
     * 被限制的提示信息
     * @return
     */
    String msg() default "接口被限制访问,请稍后再试";

    /**
     * 令牌从产生的速度,指定单位生成的令牌数量或漏桶的水流速度,默认熟读为5
     * @return
     */
    long rate() default 100;

    /**
     * 令牌或漏桶产生的单位间隔
     * @return
     */
    long interval() default 1000;

    /**
     * 单位时间内无操作，重置令牌桶
     *
     * @return
     */
    LimitUnitEnum unit() default LimitUnitEnum.PER_MINUTES;


    /**
     * 限流类型
     * @return
     */
    LimitTypeEnum type() default LimitTypeEnum.IP_TOKEN;

    /**
     * 桶的最大容量,-1不限制,0-默认速度的容量,其他为指定的容量
     * @return
     */
    long capacity() default 0;

    /**
     * 多个注解 按顺序从大到小排序
     * 排序
     * @return
     */
    int order() default 0;

    /**
     * 是否异常回滚,回退次数
     * @return
     */
    boolean rbEx() default false;
}
