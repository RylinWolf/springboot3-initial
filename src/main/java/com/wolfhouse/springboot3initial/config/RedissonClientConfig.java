package com.wolfhouse.springboot3initial.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 客户端配置类
 *
 * @author Rylin Wolf
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonClientConfig {
    private Integer port;
    private String password;
    private String username;
    private Integer database;
    private String host;

    private ObjectMapper jacksonObjectMapper;

    @Autowired
    public void setJacksonObjectMapper(@Qualifier("objectMapper") ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec(jacksonObjectMapper))
              .useSingleServer()
              .setAddress(String.format("redis://%s:%d", host, port))
              .setDatabase(database)
              .setUsername(username)
              .setPassword(password);

        return Redisson.create(config);
    }
}
