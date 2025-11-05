package com.wolfhouse.springboot3initial.exception;

/**
 * 自定义服务异常类
 *
 * @author Rylin Wolf
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }
}
