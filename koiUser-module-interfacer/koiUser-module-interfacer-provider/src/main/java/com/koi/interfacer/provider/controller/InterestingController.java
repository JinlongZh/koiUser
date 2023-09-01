package com.koi.interfacer.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 有意思的接口
 *
 * @Author zjl
 * @Date 2023/8/29 15:18
 */
@RestController
@RequestMapping("/k")
public class InterestingController {

    @GetMapping("/test")
    public String test() {
        return "调用成功";
    }

}
