package com.wolfhouse.springboot3initial.security;

import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashSet;

/**
 * 安全上下文工具
 *
 * @author Rylin Wolf
 */
public class SecurityContextUtil {
    public static UserLocalDto getLoginUser() {
        return (UserLocalDto) SecurityContextHolder.getContext()
                                                   .getAuthentication()
                                                   .getDetails();
    }

    @SuppressWarnings("unchecked")
    public static HashSet<Authentication> getAuths() {
        return new HashSet<>((Collection<Authentication>)
                                 SecurityContextHolder.getContext()
                                                      .getAuthentication()
                                                      .getAuthorities());
    }
}
