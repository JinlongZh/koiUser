package com.koi.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.koi.member.mapper")
public class KoiUserModuleMemberBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserModuleMemberBizApplication.class, args);
    }

}
