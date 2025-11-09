package com.wolfhouse.springboot3initial.config.security;

import cn.hutool.json.JSONUtil;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpMediaTypeConstant;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import org.apache.commons.codec.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

/**
 * @author Rylin Wolf
 */
@Configuration
public class EntryPointConfig {
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(HttpMediaTypeConstant.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(CharEncoding.UTF_8);
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(HttpResult.failed(HttpCode.UN_AUTHORIZED)));
            writer.flush();
            writer.close();
        };
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(HttpMediaTypeConstant.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(CharEncoding.UTF_8);
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(HttpResult.failed(HttpCode.FORBIDDEN)));
            writer.flush();
            writer.close();
        };
    }
}
