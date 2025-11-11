package com.wolfhouse.springboot3initial.handler;

import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.security.SecurityContextUtil;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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
    public ResponseEntity<HttpResult<?>> authDeniedException(AuthorizationDeniedException e) {
        UserLocalDto loginUser = SecurityContextUtil.getLoginUser();
        log.error("无权限: {}, {}\n操作用户: {}: {}",
                  e.getMessage(),
                  e.getAuthorizationResult(),
                  loginUser.getId(),
                  loginUser.getAccount());
        return HttpResult.failedWithStatus(HttpStatus.FORBIDDEN.value(),
                                           HttpCode.NO_PERMISSION);
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> sqlException(SQLSyntaxErrorException e) {
        log.error("SQL 异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpCode.SQL_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> sqlException(BadSqlGrammarException e) {
        log.error("SQL 异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpCode.SQL_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> serviceException(ServiceException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        Integer code = e.getCode();
        HttpCode httpCode = HttpCode.BAD_REQUEST;
        if (code == null) {
            code = HttpStatus.BAD_REQUEST.value();
        } else {
            httpCode = HttpCode.ofCode(code);
            code = Integer.valueOf(code.toString()
                                       .substring(0, 3));
        }
        return HttpResult.failedWithStatus(code, httpCode, e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> verifyException(VerifyException e) {
        log.error("字段校验异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.BAD_REQUEST.value(),
                                           HttpCode.BAD_REQUEST,
                                           e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<HttpResult<?>> mediaTypeException(Exception e) {
        log.error("请求异常: {}", e.getMessage(), e);
        int status;
        HttpCode code;
        switch (e.getClass()
                 .getName()) {
            case "org.springframework.web.HttpMediaTypeNotSupportedException" -> {
                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
                code = HttpCode.UNSUPPORTED_MEDIA_TYPE;
            }
            case "org.springframework.web.HttpMediaTypeNotAcceptableException" -> {
                status = HttpStatus.NOT_ACCEPTABLE.value();
                code = HttpCode.MEDIA_TYPE_NOT_ACCEPTABLE;
            }
            default -> {
                status = HttpStatus.BAD_REQUEST.value();
                code = HttpCode.BAD_REQUEST;
            }
        }
        ;
        return HttpResult.failedWithStatus(status, code);
    }

    @ExceptionHandler
    public ResponseEntity<HttpResult<?>> httpException(Exception e) {
        log.error("其他异常: {}", e.getMessage(), e);
        return HttpResult.failedWithStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpCode.UNKNOWN);
    }
}
