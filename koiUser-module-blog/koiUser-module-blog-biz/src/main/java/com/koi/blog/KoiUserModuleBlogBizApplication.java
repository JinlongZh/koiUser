package com.koi.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.koi.blog.mapper")
public class KoiUserModuleBlogBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserModuleBlogBizApplication.class, args);
    }

}
