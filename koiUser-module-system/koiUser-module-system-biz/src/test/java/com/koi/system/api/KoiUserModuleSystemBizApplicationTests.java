package com.koi.system.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class KoiUserModuleSystemBizApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private PasswordEncoder passwordEncoder;
    void passwordTest() {
        System.out.println(passwordEncoder.encode("123456"));;
        // $2a$04$KQI5yMTfIJ387KNZhgUquerjvvtR5Inm3nPdH.xGcOarwMS8MvoCq
    }

}
