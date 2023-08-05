package com.koi.member.controller;

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

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}
