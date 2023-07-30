package com.koi.system.oauth2.controller;

import com.koi.framework.redis.core.utils.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @Author zjl
 * @Date 2023/7/30 10:08
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        RedisUtils.set("test", "hahha");
        return "haha";
    }

}
