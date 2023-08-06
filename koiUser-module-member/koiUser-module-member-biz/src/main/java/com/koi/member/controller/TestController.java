package com.koi.member.controller;

import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @Author zjl
 * @Date 2023/8/5 16:15
 */
@RestController
public class TestController {

    @GetMapping("/app-api/test")
    public String test() {
        System.out.println(SecurityFrameworkUtils.getLoginUserId());
        return "test";
    }

}
