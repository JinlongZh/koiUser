package com.koi.framework.security.core.aop;


import com.koi.common.exception.ServiceException;
import com.koi.framework.security.core.annotations.PreAuthenticated;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;


@Aspect
@Slf4j
public class PreAuthenticatedAspect {

    @Around("@annotation(preAuthenticated)")
    public Object around(ProceedingJoinPoint joinPoint, PreAuthenticated preAuthenticated) throws Throwable {
        if (SecurityFrameworkUtils.getLoginUser() == null) {
            throw new ServiceException(UNAUTHORIZED);
        }
        return joinPoint.proceed();
    }

}
