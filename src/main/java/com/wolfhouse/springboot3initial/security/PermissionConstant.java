package com.wolfhouse.springboot3initial.security;

/**
 * 权限字段
 *
 * @author Rylin Wolf
 */
public interface PermissionConstant {
    String SEPARATOR = ":";
    String SERVICE = "service";
    String ADMIN = "admin";
    String USER = "user";

    String ADMIN_ALL = PermissionConstant.adminPermission(null);
    String ADMIN_ADD = PermissionConstant.adminPermission("add");
    String ADMIN_UPDATE = PermissionConstant.adminPermission("update");
    String ADMIN_DELETE = PermissionConstant.adminPermission("delete");
    String ADMIN_QUERY = PermissionConstant.adminPermission("query");

    String USER_ALL = PermissionConstant.userPermission(null);
    String USER_ADD = PermissionConstant.userPermission("add");
    String USER_UPDATE = PermissionConstant.userPermission("update");
    String USER_DELETE = PermissionConstant.userPermission("delete");

    String AUTH_ALL = PermissionConstant.adminPermission(null);
    String AUTH_ADD = PermissionConstant.authPermission("add");
    String AUTH_UPDATE = PermissionConstant.authPermission("update");
    String AUTH_DELETE = PermissionConstant.authPermission("delete");
    String AUTH_QUERY = PermissionConstant.authPermission("query");

    /**
     * 构建具有管理员权限标识的字符串。
     *
     * @param perm 权限标识，用于标记具体的管理员权限。
     * @return 包含服务前缀、管理员标识及指定权限的字符串。
     */
    static String adminPermission(String perm) {
        if (perm == null) {
            return String.join(SEPARATOR, SERVICE, ADMIN);
        }
        return String.join(SEPARATOR, SERVICE, ADMIN, perm);
    }

    /**
     * 构建具有用户权限标识的字符串。
     *
     * @param perm 权限标识，用于标记具体的用户权限。
     * @return 包含服务前缀、用户标识及指定权限的字符串。
     */
    static String userPermission(String perm) {
        if (perm == null) {
            return String.join(SEPARATOR, SERVICE, USER);
        }
        return String.join(SEPARATOR, SERVICE, USER, perm);
    }

    /**
     * 构建具有权限标识的字符串。
     *
     * @param perm 权限标识，用于标记具体的权限。若为 null，则返回包含服务前缀和默认权限标识的字符串。
     * @return 包含服务前缀、默认权限标识及指定权限的字符串。
     */
    static String authPermission(String perm) {
        if (perm == null) {
            return String.join(SEPARATOR, SERVICE, AUTH_ALL);
        }
        return String.join(SEPARATOR, SERVICE, AUTH_ALL, perm);
    }
}
