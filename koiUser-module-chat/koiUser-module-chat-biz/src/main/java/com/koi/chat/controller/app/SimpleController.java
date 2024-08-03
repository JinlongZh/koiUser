package com.koi.chat.controller.app;

import com.koi.common.domain.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;


/**
 * test
 *
 * @Author zjl
 * @Date 2024/8/3
 */
@RestController
@RequestMapping("/chat/test")
public class SimpleController {

    @PermitAll
    @GetMapping("/")
    public CommonResult<String> test() {
        return CommonResult.success("成功");
    }

}
