package com.wolfhouse.springboot3initial.security.filter;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.config.objectmapper.JacksonObjectMapper;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Rylin Wolf
 */
@Component
@RequiredArgsConstructor
public class LoginStoreFilter extends OncePerRequestFilter {
    private final UserAdminAuthMediator mediator;
    private final JacksonObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 1. 获取登录用户，验证字段
        UserLocalDto loginUser = objectMapper.convertValue(session.getAttribute(UserConstant.LOGIN_USER_SESSION_KEY),
                                                           UserLocalDto.class);
        // 未登录，直接放行
        if (loginUser == null) {
            filterChain.doFilter(request, response);
            return;
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
        if (mediator.isAdmin(loginUser.getId())) {
            loginUser.setIsAdmin(true);
            authList = mediator.getAuthByAdminId(loginUser.getId());
        }
        // 2. 保存用户名密码认证类实例至安全上下文
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(loginUser.getId(),
                                                    null,
                                                    authList);
        token.setDetails(loginUser);
        SecurityContextHolder.getContext()
                             .setAuthentication(token);
        // 3. 更新访问时间
        long time = session.getLastAccessedTime();
        LocalDateTime lastAccessedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                                                                     ZoneId.systemDefault());
        mediator.updateAccessedTime(lastAccessedDateTime);
        filterChain.doFilter(request, response);
    }
}
