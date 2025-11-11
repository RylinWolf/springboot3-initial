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
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

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

    /**
     * 处理字段验证和方法参数验证时抛出的异常。
     *
     * @param e 捕获的异常，可能是 VerifyException 或 MethodArgumentNotValidException
     * @return 包含错误详情的响应实体
     */
    @ExceptionHandler({VerifyException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<HttpResult<?>> verifyException(Exception e) {
        String msg;
        // MethodArgumentNotValidException 的完整类路径
        String methodNotValidClassPath = "org.springframework.web.bind.MethodArgumentNotValidException";

        // 判断是否为方法参数验证异常
        if (methodNotValidClassPath.equals(e.getClass()
                                            .getName())) {
            // 获取所有字段错误
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getFieldErrors();
            StringBuilder msgBuilder = new StringBuilder();
            // 构建错误信息，拼接所有字段错误
            for (FieldError fieldError : fieldErrors) {
                msgBuilder.append(fieldError.getField())
                          .append(": ")
                          .append(fieldError.getDefaultMessage())
                          .append("; ");
            }
            msg = msgBuilder.toString();
        } else {
            // 对于 VerifyException，直接使用异常消息
            msg = e.getMessage();
        }
        log.error("字段校验异常: {}", msg, e);
        return HttpResult.failedWithStatus(HttpStatus.BAD_REQUEST.value(),
                                           HttpCode.BAD_REQUEST,
                                           msg);
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
