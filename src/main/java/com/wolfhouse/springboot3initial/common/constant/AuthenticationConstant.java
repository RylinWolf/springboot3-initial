package com.wolfhouse.springboot3initial.common.constant;

/**
 * @author Rylin Wolf
 */
public interface AuthenticationConstant {
    // region Admin

    String ADMIN_NOT_EXIST = "管理员不存在";
    String NOT_ADMIN = "用户不是管理员";
    String ADMIN_NAME_EXIST = "管理员名称已存在";
    String ADMIN_ALREADY_EXIST = "管理员已存在";
    // endregion

    // region Authentication

    String AUTH_NOT_EXIST = "权限不存在";
    String AUTH_CODE_EXIST = "权限标识已存在";
    // endregion
}
