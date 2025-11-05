package com.wolfhouse.springboot3initial.common.util.beanutil;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Rylin Wolf
 */
public class ThrowUtil {
    public static Class<? extends Throwable> exceptionClazz = BeanUtilException.class;

    public static void throwIfBlank(Object o) {
        throwIfBlank(o, exceptionClazz);
    }

    public static void throwIfBlank(Object o, String msg) {
        throwIfBlank(o, msg, exceptionClazz);
    }

    public static void throwIfBlank(Object o, Integer code, String msg) {
        throwIfBlank(o, code, msg, exceptionClazz);
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

    @SneakyThrows
    public static void throwIfBlank(Object o, Integer code, String msg, Class<? extends Throwable> clazz) {
        if (BeanUtil.isBlank(o)) {
            try {
                throw clazz.getConstructor(Integer.class, String.class)
                           .newInstance(code, msg);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new BeanUtilException(e);
            }
        }
    }

    public static void throwOnCondition(boolean b, String msg) {
        throwOnCondition(b, msg, exceptionClazz);
    }

    public static void throwOnCondition(boolean b, Integer code, String msg) {
        throwOnCondition(b, code, msg, exceptionClazz);
    }

    @SneakyThrows
    public static void throwOnCondition(boolean b, String msg, Class<? extends Throwable> clazz) {
        if (!b) {
            return;
        }
        try {
            throw clazz.getConstructor(String.class)
                       .newInstance(msg);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new BeanUtilException(e);
        }
    }

    @SneakyThrows
    public static void throwOnCondition(boolean b, Integer code, String msg, Class<? extends Throwable> clazz) {
        if (!b) {
            return;
        }
        try {
            throw clazz.getConstructor(Integer.class, String.class)
                       .newInstance(code, msg);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new BeanUtilException(e);
        }
    }
}
