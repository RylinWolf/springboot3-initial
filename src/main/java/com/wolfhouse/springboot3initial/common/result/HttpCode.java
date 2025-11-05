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
    PARAM_ERROR(40010, "参数错误"),
    UNKNOWN(50099, "未知错误"),
    SQL_ERROR(50011, "SQL 错误"),
    ;

    public final int code;
    public final String message;

    HttpCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
