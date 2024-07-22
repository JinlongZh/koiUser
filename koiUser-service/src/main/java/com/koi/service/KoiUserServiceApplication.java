package com.koi.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.koi")
public class KoiUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserServiceApplication.class, args);
    }

}
