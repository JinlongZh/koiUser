package com.koi.koiuserserver.controller;

import com.koi.common.result.Result;
import com.koi.framework.redis.core.utils.RedisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/27 15:59
 */
@RestController
public class AuthController {

    @PostMapping("/login")
    public Result login() {
        return null;
    }

}
