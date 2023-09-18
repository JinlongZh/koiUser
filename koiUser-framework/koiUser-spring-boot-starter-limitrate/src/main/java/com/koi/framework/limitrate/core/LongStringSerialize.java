package com.koi.framework.limitrate.core;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 17:31
 */
public class LongStringSerialize implements RedisSerializer<Long> {
    @Override
    public byte[] serialize(Long number) throws SerializationException {

        return String.valueOf(number).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        return Long.valueOf(new String(bytes,StandardCharsets.UTF_8));
    }
}