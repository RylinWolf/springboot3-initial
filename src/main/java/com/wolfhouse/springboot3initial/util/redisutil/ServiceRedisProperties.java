package com.wolfhouse.springboot3initial.util.redisutil;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Rylin Wolf
 */
@ConfigurationProperties(prefix = "custom.redis")
public record ServiceRedisProperties(String keyPrefix,
                                     String separator,
                                     Long loginExpire,
                                     Long maxLoginTtl) {

}
