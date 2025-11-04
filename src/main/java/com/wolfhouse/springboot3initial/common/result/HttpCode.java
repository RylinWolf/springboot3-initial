package com.wolfhouse.springboot3initial.common.result;

import org.springframework.http.HttpStatus;

/**
 * Http 状态封装类
 *
 * @author Rylin Wolf
 */
public enum HttpCode {
    /** 成功 */
    SUCCESS(HttpStatus.OK.value(), ""),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "访问被拒绝"),
    UNKNOWN(50099, "未知错误");
    
    public final int code;
    public final String message;

    HttpCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
