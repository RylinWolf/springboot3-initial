package com.wolfhouse.springboot3initial.config.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfhouse.springboot3initial.common.result.HttpMediaTypeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Rylin Wolf
 */
@Configuration
public class MessageConverterConfig implements WebMvcConfigurer {
    private final ObjectMapper jsonNullableMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageConverterConfig(@Qualifier("jsonNullableMapper") ObjectMapper jsonNullableMapper,
                                  @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.jsonNullableMapper = jsonNullableMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        var nullableConverter = new MappingJackson2HttpMessageConverter(jsonNullableMapper);
        nullableConverter.setSupportedMediaTypes(List.of(HttpMediaTypeConstant.APPLICATION_JSON_NULLABLE));
        var defaultConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        converters.add(1, nullableConverter);
        converters.add(1, defaultConverter);
    }
}
