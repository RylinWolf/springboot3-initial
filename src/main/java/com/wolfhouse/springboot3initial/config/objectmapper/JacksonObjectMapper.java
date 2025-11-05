package com.wolfhouse.springboot3initial.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义的 ObjectMapper, 兼容 LocalDateTime 等时间类型
 *
 * @author Rylin Wolf
 */
public class JacksonObjectMapper extends ObjectMapper {

    public JacksonObjectMapper(DateFormatConfig dateFormatConfig) {
        // 配置未知属性不警告
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置日期不写为时间戳
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DateTimeFormatter datetime = DateTimeFormatter.ofPattern(dateFormatConfig.datetime());
        DateTimeFormatter date = DateTimeFormatter.ofPattern(dateFormatConfig.date());
        DateTimeFormatter time = DateTimeFormatter.ofPattern(dateFormatConfig.time());

        // 注册时间转化器模型
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(datetime));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(date));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(time));

        module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        module.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        module.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);

        this.registerModule(module);
    }
}
