package com.wolfhouse.springboot3initial.common.util.redisutil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.Duration;

/**
 * 操作 Redis 的工具类
 *
 * @author Rylin Wolf
 */
public class RedisUtil {
    public final RedisTemplate<String, Object> redisTemplate;
    public final ValueOperations<String, Object> opsForValue;
    public final SetOperations<String, Object> opsForSet;
    public final ZSetOperations<String, Object> opsForZSet;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForValue = redisTemplate.opsForValue();
        this.opsForSet = redisTemplate.opsForSet();
        this.opsForZSet = redisTemplate.opsForZSet();
    }

    public Long getAndIncrease(String key, int value) {
        return opsForValue.increment(key, value);
    }

    public Long getAndDecrease(String key, int value) {
        return opsForValue.decrement(key, value);
    }

    // region set 方法

    public void addSetValue(String key, Object value) {
        opsForSet.add(key, value);
    }

    public void addSetValueExpire(String key, Object value, Duration duration) {
        opsForSet.add(key, value, duration);
    }

    public void removeSetValue(String key, Object value) {
        opsForSet.remove(key, value);
    }

    public Object popSetValue(String key, Object value) {
        return opsForSet.pop(key);
    }

    public Long sizeOfSetValue(String key) {
        return opsForSet.size(key);
    }

    public Boolean isSetValueMember(String key, Object value) {
        return opsForSet.isMember(key, value);
    }

    public Boolean addZSetValue(String key, Object value, double score) {
        return opsForZSet.add(key, value, score);
    }

    public Long removeZSetValue(String key) {
        return opsForZSet.remove(key);
    }

    public Double incrementZSetValue(String key, Object value, double score) {
        return opsForZSet.incrementScore(key, value, score);
    }


    // endregion


    // region value 方法

    // TODO

    public void setValue(String key, Object value) {
        this.opsForValue.set(key, value);
    }

    public void setValueExpire(String key, Object value, Duration duration) {
        this.opsForValue.set(key, value, duration);
    }

    public void setExpire(String key, Duration duration) {
        this.redisTemplate.expire(key, duration);
    }

    public Boolean setValueIfAbsent(String key, Object value) {
        return this.opsForValue.setIfAbsent(key, value);
    }

    public Object getValue(String key) {
        return this.opsForValue.get(key);
    }

    public Object getValueAndExpire(String key, Duration duration) {
        Object value = this.opsForValue.get(key);
        this.redisTemplate.expire(key, duration);
        return value;
    }

    public Object getValueAndDelete(String key) {
        return opsForValue.getAndDelete(key);
    }

    public Boolean deleteValue(String key) {
        return this.redisTemplate.delete(key);
    }

    public Boolean isKeyExist(String key) {
        return this.redisTemplate.hasKey(key);
    }
    // endregion

    // region 内置方法

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean expire(String key, Duration duration) {
        return redisTemplate.expire(key, duration);
    }
    // endregion
}
