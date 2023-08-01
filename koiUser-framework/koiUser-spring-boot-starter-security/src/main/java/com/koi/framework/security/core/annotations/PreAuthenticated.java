package com.koi.framework.security.core.annotations;

import java.lang.annotation.*;

/**
 * 声明用户需要登录
 *
 * @Author zjl
 * @Date 2023/8/1 21:22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthenticated {
}
