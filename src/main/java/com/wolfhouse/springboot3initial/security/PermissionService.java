package com.wolfhouse.springboot3initial.security;

import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Rylin Wolf
 */
@Component("pm")
public class PermissionService {
    public static Boolean isAdmin() {
        UserLocalDto user = (UserLocalDto) SecurityContextHolder.getContext()
                                                                .getAuthentication()
                                                                .getDetails();
        return user.getIsAdmin();
    }

    public static Boolean hasAnyPerm(String... permissions) {
        // 构建权限集合
        HashSet<Authentication> perms = new HashSet<>();
        for (String permission : permissions) {
            perms.add(new Authentication(permission));
        }
        // 交集，保留集合共有元素
        perms.retainAll(getAuth());
        // 集合为空则无权限
        return !perms.isEmpty();
    }

    /**
     * 检查当前认证用户是否具有指定的权限。
     *
     * @param permission 指定的权限标识，用于检查当前用户是否具备该权限。
     * @return 如果当前用户具备指定的权限则返回 true，否则返回 false。
     */
    public static Boolean hasPerm(String permission) {
        return getAuth().contains(new Authentication(permission));
    }

    /**
     * 获取当前认证用户的认证信息。
     *
     * @return 当前认证用户的 {@link Authentication} 对象，如果未认证则返回 null。
     */
    @SuppressWarnings("unchecked")
    public static HashSet<com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication> getAuth() {
        return new HashSet<>((Collection<Authentication>)
                                 SecurityContextHolder.getContext()
                                                      .getAuthentication()
                                                      .getAuthorities());
    }
}
