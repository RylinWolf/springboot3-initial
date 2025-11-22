package com.wolfhouse.springboot3initial.util.redisutil.constant;

import com.wolfhouse.springboot3initial.common.util.redisutil.RedisKey;
import org.springframework.stereotype.Component;

/**
 * @author Rylin Wolf
 */
@RedisKey(isKeysConstant = true)
@Component
public class OssRedisConstant {
    public static final String OSS_DELETE_SET = "fileDelete";
}
