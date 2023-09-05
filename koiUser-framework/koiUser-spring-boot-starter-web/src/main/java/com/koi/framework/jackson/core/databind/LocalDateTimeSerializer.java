package com.koi.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime序列化规则
 *
 * @Author zjl
 * @Date 2023/9/5 15:32
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String dateTimeString = value.format(FORMATTER);
        gen.writeString(dateTimeString);
    }
}
