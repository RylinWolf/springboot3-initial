package com.wolfhouse.springboot3initial.config.objectmapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
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
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TINE_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper(DateFormatConfig dateFormatConfig) {
        this();
        init(dateFormatConfig);
    }

    public JacksonObjectMapper() {
        super();
    }

    public JacksonObjectMapper(ObjectMapper src) {
        super(src);
        init(null);

    }

    public JacksonObjectMapper(ObjectMapper src, DateFormatConfig dateFormatConfig) {
        super(src);
        init(dateFormatConfig);
    }

    public JacksonObjectMapper(JsonFactory jsonFactory) {
        super(jsonFactory);
        init(null);
    }

    public void init(DateFormatConfig dateFormatConfig) {
        // 配置未知属性不警告
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置日期不写为时间戳
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        if (dateFormatConfig == null) {
            dateFormatConfig = new DateFormatConfig(DEFAULT_DATE_TIME_FORMAT,
                                                    DEFAULT_DATE_FORMAT,
                                                    DEFAULT_TINE_FORMAT);
        }
        DateTimeFormatter datetime = DateTimeFormatter.ofPattern(dateFormatConfig.datetime());
        DateTimeFormatter date = DateTimeFormatter.ofPattern(dateFormatConfig.date());
        DateTimeFormatter time = DateTimeFormatter.ofPattern(dateFormatConfig.time());
        // 注册时间转化器模型
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(datetime));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(date));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(time));

        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(datetime));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(date));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(time));

        this.registerModule(module);

        // 配置空字符串转换
        this.coercionConfigDefaults()
            .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsEmpty);
    }

    @Override
    public ObjectMapper copy() {
        JacksonObjectMapper m = new JacksonObjectMapper();
        m.init(null);
        return m;
    }

    @Override
    public ObjectMapper copyWith(JsonFactory factory) {
        JacksonObjectMapper m = new JacksonObjectMapper(factory);
        m.init(null);
        return m;
    }
}
