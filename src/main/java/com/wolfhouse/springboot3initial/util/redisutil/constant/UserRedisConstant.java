package com.wolfhouse.springboot3initial.util.redisutil.constant;

import com.wolfhouse.springboot3initial.common.util.redisutil.RedisKey;
import org.springframework.stereotype.Component;

/**
 * @author Rylin Wolf
 */
@Component
@RedisKey(isKeysConstant = true)
public class UserRedisConstant {
    public static final String LAST_LOGIN = "lastLogin";
    @RedisKey(secondPrefix = "last:login", name = "%s")
    public static final String LAST_LOGIN_WITH_FORMAT = "lastLoginWithF";

    @RedisKey(asName = true, name = "%s")
    public static final String USER_AVATAR = "userAvatar";

}
