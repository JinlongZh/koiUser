package com.koi.framework.limitrate.core.annotation;

import java.lang.annotation.*;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitRates {
    LimitRate[] value();
}
