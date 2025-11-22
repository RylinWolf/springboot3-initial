package com.wolfhouse.springboot3initial.util.redisutil.constant;

import com.wolfhouse.springboot3initial.common.util.redisutil.Except;
import com.wolfhouse.springboot3initial.common.util.redisutil.RedisKey;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author Rylin Wolf
 */
@Component
@RedisKey(isKeysConstant = true)
public class UserRedisConstant {
    public static final String LAST_LOGIN = "lastLogin";
    @RedisKey(secondPrefix = "last:login", asName = false, name = "%s")
    public static final String LAST_LOGIN_WITH_FORMAT = "lastLoginWithF";

    @RedisKey(name = "%s")
    public static final String USER_AVATAR = "userAvatar";

    @RedisKey(name = "%s")
    public static final String USER_VO = "userVo";

    @RedisKey(name = "%s")
    public static final String USER_AUTH = "userAuth";

    @Except
    public static final Duration USER_AUTH_DURATION = Duration.ofMinutes(3 * 24 * 60);

    @RedisKey(name = "%s")
    public static final String USER_EXIST = "userExist";

    @Except
    public static final Duration EXIST_DURATION = Duration.ofMinutes(10);
    public static final Duration VO_DURATION = Duration.ofMinutes(3 * 24 * 60);
    public static final Duration AVATAR_DURATION = Duration.ofMinutes(60);
}
