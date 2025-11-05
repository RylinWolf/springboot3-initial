package com.wolfhouse.springboot3initial.common.util.beanutil;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Rylin Wolf
 */
public class ThrowUtil {
    public static void throwIfBlank(Object o) {
        throwIfBlank(o, BeanUtilException.class);
    }

    public static void throwIfBlank(Object o, String msg) {
        throwIfBlank(o, msg, BeanUtilException.class);
    }

    public static void throwIfBlank(Object o, Class<? extends Throwable> clazz) {
        throwIfBlank(o, "对象不得为空", clazz);
    }

    @SneakyThrows
    public static void throwIfBlank(Object o, String msg, Class<? extends Throwable> clazz) {
        if (BeanUtil.isBlank(o)) {
            try {
                throw clazz.getConstructor(String.class)
                           .newInstance(msg);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new BeanUtilException(e);
            }
        }
    }
}
