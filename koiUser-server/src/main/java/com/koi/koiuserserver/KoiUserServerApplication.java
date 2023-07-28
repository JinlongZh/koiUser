package com.koi.koiuserserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.koi"})
@MapperScan
public class KoiUserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserServerApplication.class, args);
    }

}
