package com.koi.system.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class KoiUserModuleSystemBizApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        clientAndSecretTest();
    }
    public static void clientAndSecretTest() {
        String client = "koiuser-sso-demo-by-code" + ":" + "test";
        client = Base64Utils.encodeToString(client.getBytes(StandardCharsets.UTF_8));
        System.out.println("Basic " + client);
    }


    @Resource
    private PasswordEncoder passwordEncoder;
    void passwordTest() {
        System.out.println(passwordEncoder.encode("123456"));;
        // $2a$04$KQI5yMTfIJ387KNZhgUquerjvvtR5Inm3nPdH.xGcOarwMS8MvoCq
    }

}
