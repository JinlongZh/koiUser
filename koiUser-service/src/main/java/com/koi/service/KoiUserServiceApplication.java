package com.koi.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.koi")
@MapperScan(basePackages = "com.koi.**.mapper")
public class KoiUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserServiceApplication.class, args);
    }

}
