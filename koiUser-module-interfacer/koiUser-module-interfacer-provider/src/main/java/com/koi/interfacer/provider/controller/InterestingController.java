package com.koi.interfacer.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 有意思的接口
 *
 * @Author zjl
 * @Date 2023/8/29 15:18
 */
@RestController
@RequestMapping("/k")
public class InterestingController {

    @GetMapping("/rand.avatar")
    public String randAvatar(HttpServletRequest request) {
//        String url = "https://api.uomg.com/api/rand.avatar";
//        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
//        HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
//                .execute();
//        return httpResponse.body();
        return "调用成功";
    }

}
