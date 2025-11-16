package com.wolfhouse.springboot3initial.config.util;

import com.wolfhouse.springboot3initial.common.util.redisutil.RedisKeyUtil;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Rylin Wolf
 */
@Configuration
public class ServiceRedisUtilConfig {
    @Bean
    public ServiceRedisUtil serviceRedisUtil(RedisTemplate<String, Object> template, RedisKeyUtil keyUtil) {
        return new ServiceRedisUtil(template, keyUtil);
    }
}
