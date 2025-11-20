package com.wolfhouse.springboot3initial.security.config;

import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLoginDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.UserRedisConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Rylin Wolf
 */
@Component
@RequiredArgsConstructor
public class AuthenticationProviderConfig implements AuthenticationProvider {
    private final UserService userService;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;
    private final UserAdminAuthMediator mediator;
    private final ServiceRedisUtil redisUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 通过用户服务验证是否登录成功
        String certificate = authentication.getName();
        String password = authentication.getCredentials()
                                        .toString();

        // 尝试登录
        UserLocalDto localDto = mediator.tryLogin(new UserLoginDto(certificate, password));
        // 登录不成功
        if (localDto == null) {
            return authentication;
        }
        // 初始化权限列表
        List<com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication> authList = List.of();
        // 注入权限
        if (Optional.ofNullable(localDto.getIsAdmin())
                    .orElse(false)) {
            authList = mediator.getAuthByAdminId(localDto.getId());
            // 缓存权限
            redisUtil.setValueExpire(UserRedisConstant.USER_AUTH,
                                     authList,
                                     UserRedisConstant.USER_AUTH_DURATION,
                                     localDto.getId());
        }
        // 2. 登录成功返回用户名密码认证类实例
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(certificate,
                                                    null,
                                                    authList);

        // 将 userLocal 注入到认证对象的 details 中，便于外层保存至 session
        token.setDetails(localDto);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
