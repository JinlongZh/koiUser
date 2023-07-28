package com.koi.koiuserserver;

import com.koi.framework.redis.core.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KoiUserServerApplicationTests {

    @Test
    void contextLoads() {
        RedisUtils.set("test", "test");
    }

}
