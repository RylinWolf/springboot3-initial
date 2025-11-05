package com.wolfhouse.springboot3initial.exception;

import lombok.Getter;

/**
 * 自定义服务异常类
 *
 * @author Rylin Wolf
 */
@Getter
public class ServiceException extends RuntimeException {
    private Integer code = 500;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
