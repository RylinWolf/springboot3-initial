package com.wolfhouse.springboot3initial.config.util;

import com.wolfhouse.springboot3initial.common.util.redisutil.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Rylin Wolf
 */
@Configuration
public class RedisUtilConfig {
    @Bean
    public RedisUtil redisUtil(RedisTemplate<String, Object> template) {
        return new RedisUtil(template);
    }
}
