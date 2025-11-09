package com.wolfhouse.springboot3initial.security;

/**
 * @author Rylin Wolf
 */
public class SecurityConstant {
    public static final String[] WHITELIST = {
        "/v3/api-docs/**",
        "/webjars/**",
        "/user/login",
        };
    public static final String[] STATIC_PATH_WHITELIST = {
        "/",
        "/js/**",
        "/css/**",
        "/img/**",
        "/fonts/**",
        "/index.html",
        "/favicon.ico",
        "/doc.html",
        };
}
