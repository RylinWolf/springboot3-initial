package com.wolfhouse.springboot3initial.util;

import com.mybatisflex.core.query.QueryWrapper;
import org.openapitools.jackson.nullable.JsonNullable;

import java.lang.reflect.Field;

/**
 * JsonNullable 工具类
 * <p>
 * TODO 动态构建 wrapper
 *
 * @author Rylin Wolf
 */
public class JsonNullableUtil {
    @Deprecated(since = "未完成")
    public static <T> QueryWrapper buildWrapper(QueryWrapper wrapper, T source, Class<T> tClass) {
        Field[] fields = tClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!JsonNullable.class.isAssignableFrom(field.getType())) {
                continue;
            }
            System.out.println(field.getName());
        }
        return wrapper;
    }
}
