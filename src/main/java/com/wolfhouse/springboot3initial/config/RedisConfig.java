package com.wolfhouse.springboot3initial.config;

import com.wolfhouse.springboot3initial.config.objectmapper.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author Rylin Wolf
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(
        @Qualifier("objectMapper") JacksonObjectMapper objectMapper) {
        // 使用 JSON 序列化器,支持更好的兼容性
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory,
                                                       @Qualifier("objectMapper") JacksonObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        template.afterPropertiesSet();
        return template;
    }
}
