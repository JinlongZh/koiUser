package com.koi.interfacer.provider.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 有意思的接口
 *
 * @Author zjl
 * @Date 2023/8/29 15:18
 */
@RestController
@RequestMapping("/k")
public class InterestingController {

    @GetMapping("/get")
    public String testGet(@RequestParam("id") Integer id, @RequestParam("value") String value) {
        return "get调用成功" + id + ":" + value;
    }

    @PostMapping("/post")
    public String testPost(@RequestBody Test test) {
        return "post调用成功" + test.getId() + ":" + test.getValue();
    }

}
