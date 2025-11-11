package com.wolfhouse.springboot3initial.config.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 注册消息转换器，支持 JSONNullable
 *
 * @author Rylin Wolf
 */
@Configuration
public class MessageConverterConfig implements WebMvcConfigurer {
    private final ObjectMapper jsonNullableMapper;

    @Autowired
    public MessageConverterConfig(@Qualifier("jsonNullableMapper") ObjectMapper jsonNullableMapper) {
        // 全局注册
        this.jsonNullableMapper = jsonNullableMapper;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        var defaultConverter = new MappingJackson2HttpMessageConverter(jsonNullableMapper);
        converters.add(1, defaultConverter);
    }
}
