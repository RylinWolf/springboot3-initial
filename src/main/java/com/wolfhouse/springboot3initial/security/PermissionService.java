package com.wolfhouse.springboot3initial.security;

import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author Rylin Wolf
 */
@Component("pm")
public class PermissionService {
    public static Boolean isAdmin() {
        return SecurityContextUtil.getLoginUser()
                                  .getIsAdmin();
    }

    public static Boolean hasAnyPerm(String... permissions) {
        // 构建权限集合
        HashSet<Authentication> perms = new HashSet<>();
        for (String permission : permissions) {
            perms.add(new Authentication(permission));
        }
        // 交集，保留集合共有元素
        perms.retainAll(SecurityContextUtil.getAuths());
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
        return SecurityContextUtil.getAuths()
                                  .contains(new Authentication(permission));
    }
}
