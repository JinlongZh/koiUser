package com.koi.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime反序列化规则
 *
 * @Author zjl
 * @Date 2023/9/5 15:30
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateTimeString = p.getValueAsString();
        return LocalDateTime.parse(dateTimeString, FORMATTER);
    }
}
