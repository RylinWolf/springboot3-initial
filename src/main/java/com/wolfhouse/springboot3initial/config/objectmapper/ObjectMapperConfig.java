package com.wolfhouse.springboot3initial.config.objectmapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置自定义的各种 objectMapper
 *
 * @author Rylin Wolf
 */
@Configuration
@RequiredArgsConstructor
public class ObjectMapperConfig {
    private final DateFormatConfig dateFormatConfig;

    public static JacksonObjectMapper defaultMapper(DateFormatConfig dateFormatConfig) {
        return new JacksonObjectMapper(dateFormatConfig);
    }

    @Bean
    public JacksonObjectMapper objectMapper() {
        return defaultMapper(dateFormatConfig);
    }

    @Bean
    public JacksonObjectMapper jsonNullableMapper() {
        JacksonObjectMapper om = defaultMapper(dateFormatConfig);
        //        om.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        om.registerModule(new JsonNullableModule());
        return om;
    }

    @Bean
    public JacksonObjectMapper redisObjectMapper() {
        JacksonObjectMapper om = defaultMapper(dateFormatConfig);
        // 配置指定保存类型信息，解决 Redis LinkedHashMap 的问题
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                                 ObjectMapper.DefaultTyping.NON_FINAL,
                                 JsonTypeInfo.As.PROPERTY);
        return om;
    }

    /**
     * 蛇行命名(snake_case)
     *
     * @return 蛇行命名规则的 objectMapper
     */
    @Bean
    public JacksonObjectMapper snakeCaseMapper() {
        JacksonObjectMapper om = defaultMapper(dateFormatConfig);
        om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return om;
    }
}
