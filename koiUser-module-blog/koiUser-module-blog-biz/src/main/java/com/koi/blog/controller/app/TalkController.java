package com.koi.blog.controller.app;

import com.koi.blog.service.TalkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 说说API
 *
 * @Author zjl
 * @Date 2023/12/16 21:48
 */
@Tag(name = "说说API")
@RestController
@RequestMapping("/blog/talk")
public class TalkController {

    @Resource
    private TalkService talkService;

}
