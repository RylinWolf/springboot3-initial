package com.wolfhouse.springboot3initial.util;

import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;

/**
 * 基于 ThreadLocal 的用户登录状态保存器
 *
 * @author Rylin Wolf
 */
public class LocalLoginUtil {
    private static final ThreadLocal<UserLocalDto> LOCAL_USER = new ThreadLocal<>();

    public static UserLocalDto getUser() {
        return LOCAL_USER.get();
    }

    public static void setUser(UserLocalDto user) {
        LOCAL_USER.set(user);
    }

    public static void removeUser() {
        LOCAL_USER.remove();
    }
}
