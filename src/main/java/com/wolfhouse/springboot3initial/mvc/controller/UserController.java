package com.wolfhouse.springboot3initial.mvc.controller;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.*;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户接口")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public HttpResult<UserVo> login(@RequestBody @Valid UserLoginDto dto,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        // 1. 通过 authenticationManager 验证
        Authentication authenticate =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                    dto.getCertification(),
                    dto.getPassword()));
        // 2. 判断验证是否通过
        if (!authenticate.isAuthenticated()) {
            // 未通过
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return HttpResult.failed(HttpCode.PARAM_ERROR, UserConstant.LOGIN_FAILED, null);
        }
        // 3. 通过，将 provider 中注入的 userLocal 保存到 session
        UserLocalDto localDto = (UserLocalDto) authenticate.getDetails();
        request.getSession()
               .setAttribute(UserConstant.LOGIN_USER_SESSION_KEY,
                             localDto);
        return HttpResult.success(userService.getVoById(localDto.getId()));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "注销登录")
    public HttpResult<?> logout(HttpServletRequest request) {
        request.getSession()
               .removeAttribute(UserConstant.LOGIN_USER_SESSION_KEY);
        return HttpResult.success();
    }

    @PostMapping
    @Operation(summary = "用户注册")
    public HttpResult<UserVo> register(@RequestBody UserRegisterDto dto) {
        // 1. 调用业务方法
        UserVo vo = userService.register(dto);
        // 2. 返回结果
        return HttpResult.failedIfBlank(vo);
    }

    @GetMapping
    @Operation(summary = "获取当前登录用户")
    public HttpResult<UserVo> getLogin() {
        UserLocalDto login = userService.getLogin();
        if (login == null) {
            return HttpResult.failed(HttpCode.UN_AUTHORIZED, null);
        }
        return HttpResult.success(userService.getVoById(login.getId()));
    }

    /**
     * 根据主键删除用户表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "用户删除")
    @PreAuthorize("@pm.hasPerm('service:user:delete')")
    public HttpResult<?> remove(@PathVariable Long id) {
        return HttpResult.onCondition(HttpCode.UNKNOWN, userService.removeById(id));
    }


    /**
     * 根据用户表主键获取详细信息。
     *
     * @param id user主键
     * @return 用户表详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "按 ID 获取用户 Vo")
    public HttpResult<UserVo> getInfo(@PathVariable Long id) {
        return HttpResult.failedIfBlank(userService.getVoById(id));
    }

    @Operation(summary = "更新用户")
    @PatchMapping
    public HttpResult<UserVo> update(@RequestBody UserUpdateDto dto, HttpServletRequest request) {
        // 更新用户
        UserVo vo = userService.update(dto);
        // 更新成功，保存更新后的信息到 Session
        if (vo != null) {
            UserLocalDto localDto = BeanUtil.copyProperties(vo, UserLocalDto.class);
            request.getSession()
                   .setAttribute(UserConstant.LOGIN_USER_SESSION_KEY, localDto);
        }
        return HttpResult.failedIfBlank(vo);
    }

    @PutMapping("/pwd")
    @Operation(summary = "更新密码")
    public HttpResult<?> updatePassword(@RequestBody UserPwdUpdateDto dto, HttpServletRequest request) {
        Boolean b = userService.updatePassword(dto);
        // 更新结束，使登录凭证失效
        logout(request);
        return HttpResult.success(b);
    }

}