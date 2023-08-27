package com.koi.koiAuth.framework.core.utils;


import com.koi.koiAuth.framework.core.dto.LoginUser;

/**
 * 请求上下文
 *
 * @Author zjl
 * @Date 2023/8/27 12:07
 */
public class RequestHolder {

    private static final ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    public static void set(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }

    public static LoginUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
