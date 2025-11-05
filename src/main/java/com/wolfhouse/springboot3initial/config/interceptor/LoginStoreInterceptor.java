package com.wolfhouse.springboot3initial.config.interceptor;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
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
        User loginUser = (User) request.getSession()
                                       .getAttribute(UserConstant.LOGIN_USER_SESSION_KEY);
        if (loginUser != null) {
            LocalLoginUtil.setUser(loginUser);
        }
        return true;
    }
}
