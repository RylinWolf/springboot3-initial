package com.wolfhouse.springboot3initial.util;

import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;

/**
 * 基于 ThreadLocal 的用户登录状态保存器
 *
 * @author Rylin Wolf
 */
public class LocalLoginUtil {
    private static final ThreadLocal<User> LOCAL_USER = new ThreadLocal<>();

    public static User getUser() {
        return LOCAL_USER.get();
    }

    public static void setUser(User user) {
        LOCAL_USER.set(user);
    }

    public static void removeUser() {
        LOCAL_USER.remove();
    }
}
