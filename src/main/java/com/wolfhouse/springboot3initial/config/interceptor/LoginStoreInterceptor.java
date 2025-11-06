package com.wolfhouse.springboot3initial.config.interceptor;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * @author Rylin Wolf
 */
@Component
@RequiredArgsConstructor
public class LoginStoreInterceptor implements HandlerInterceptor {
    private final AuthenticationService authenticationService;
    private final UserAdminAuthMediator mediator;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        // 1. 获取登录用户，验证字段
        UserLocalDto loginUser = (UserLocalDto) request.getSession()
                                                       .getAttribute(UserConstant.LOGIN_USER_SESSION_KEY);
        // 未登录，直接放行
        if (loginUser == null) {
            return true;
        }
        // 2. 获取登录用户权限，保存至安全上下文
        if (!mediator.isUserExist(loginUser.getId())) {
            // 用户不存在，清除 session
            request.getSession()
                   .setAttribute(UserConstant.LOGIN_USER_SESSION_KEY, null);
            throw new ServiceException(HttpCode.UN_AUTHORIZED);
        }
        // 初始化权限列表
        List<Authentication> authList = List.of();
        // 注入权限
        if (loginUser.getIsAdmin()) {
            authList = mediator.getAuthByAdminId(loginUser.getId());
        }
        // 2. 保存用户名密码认证类实例至安全上下文
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(loginUser.getId(),
                                                    null,
                                                    authList);
        SecurityContextHolder.getContext()
                             .setAuthentication(token);
        return true;
    }
}
