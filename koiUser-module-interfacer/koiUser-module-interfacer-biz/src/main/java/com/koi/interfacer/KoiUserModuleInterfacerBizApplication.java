package com.koi.interfacer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.koi.interfacer.mapper")
public class KoiUserModuleInterfacerBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiUserModuleInterfacerBizApplication.class, args);
    }

}
