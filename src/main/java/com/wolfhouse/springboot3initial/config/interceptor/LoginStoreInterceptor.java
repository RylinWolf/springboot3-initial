package com.wolfhouse.springboot3initial.config.interceptor;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.util.LocalLoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Rylin Wolf
 */
public class LoginStoreInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        UserLocalDto loginUser = (UserLocalDto) request.getSession()
                                                       .getAttribute(UserConstant.LOGIN_USER_SESSION_KEY);
        if (loginUser != null) {
            LocalLoginUtil.setUser(loginUser);
        }
        return true;
    }
}
