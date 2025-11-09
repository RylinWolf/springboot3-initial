package com.wolfhouse.springboot3initial.config.security;

import com.wolfhouse.springboot3initial.config.filter.LoginStoreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Rylin Wolf
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AccessDeniedHandler accessDeniedHandler,
                                           AuthenticationEntryPoint entryPoint,
                                           LoginStoreFilter loginFilter) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(a -> {
                a.requestMatchers(SecurityConstant.WHITELIST)
                 .permitAll();
                a.requestMatchers(SecurityConstant.STATIC_PATH_WHITELIST)
                 .permitAll();
                a.anyRequest()
                 .authenticated();
            })
            .exceptionHandling((e) -> e.authenticationEntryPoint(entryPoint)
                                       .accessDeniedHandler(accessDeniedHandler))
            .addFilterAfter(loginFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
