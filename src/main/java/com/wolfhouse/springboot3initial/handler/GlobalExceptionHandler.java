package com.wolfhouse.springboot3initial.handler;

import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

/**
 * 全局异常处理器
 *
 * @author Rylin Wolf
 */
@Slf4j
@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> sqlException(SQLSyntaxErrorException e) {
        log.error("SQL 异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpCode.SQL_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> httpException(Exception e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpCode.UNKNOWN);
    }
}
