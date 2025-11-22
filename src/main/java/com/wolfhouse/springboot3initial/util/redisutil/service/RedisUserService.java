package com.wolfhouse.springboot3initial.util.redisutil.service;

import com.wolfhouse.springboot3initial.config.objectmapper.JacksonObjectMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisProperties;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.UserRedisConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rylin Wolf
 */
@Component
@RequiredArgsConstructor
public class RedisUserService {
    private final ServiceRedisUtil redisUtil;
    private final ServiceRedisProperties properties;
    private JacksonObjectMapper redisObjectMapper;


    @Autowired
    public void setRedisObjectMapper(@Qualifier("redisObjectMapper") JacksonObjectMapper redisObjectMapper) {
        this.redisObjectMapper = redisObjectMapper;
    }

    // region 清除缓存

    public void clearForUpdate(Long userId) {
        redisUtil.delete(UserRedisConstant.USER_VO, userId);
    }

    public void clearAuth(Long userId) {
        redisUtil.delete(UserRedisConstant.USER_AUTH, userId);
        redisUtil.delete(UserRedisConstant.USER_EXIST, userId);
    }

    public void clearAll(Long userId) {
        this.clearForUpdate(userId);
        this.clearAuth(userId);
    }

    // endregion

    public Boolean userExist(Long userId) {
        return redisUtil.hasKey(UserRedisConstant.USER_EXIST, userId);
    }

    public void setExist(Long userId) {
        redisUtil.setValueExpire(UserRedisConstant.USER_EXIST, null, UserRedisConstant.EXIST_DURATION, userId);
    }

    public Boolean authExist(Long userId) {
        return redisUtil.hasKey(UserRedisConstant.USER_AUTH, userId);
    }

    public void setAuth(Long userId, List<Authentication> authentications) {
        redisUtil.setValueExpire(UserRedisConstant.USER_AUTH,
                                 authentications,
                                 UserRedisConstant.USER_AUTH_DURATION,
                                 userId);
    }

    @SuppressWarnings("unchecked")
    public List<Authentication> getAuth(Long userId) {
        try {
            return (List<Authentication>) redisUtil.getValue(UserRedisConstant.USER_AUTH, userId);
        } catch (Exception ignored) {}
        return null;
    }

    public LocalDateTime lastLogin(Long userId) {
        try {
            return redisObjectMapper.convertValue(redisUtil.getValueAndExpire(UserRedisConstant.LAST_LOGIN_WITH_FORMAT,
                                                                              Duration.ofMinutes(properties.loginExpire()),
                                                                              userId), LocalDateTime.class);
        } catch (Exception ignored) {}
        return null;
    }

    public void setLastLogin(Long userId, LocalDateTime lastLogin) {
        redisUtil.setValueExpire(UserRedisConstant.LAST_LOGIN_WITH_FORMAT,
                                 lastLogin,
                                 Duration.ofMinutes(properties.loginExpire()),
                                 userId);
    }

    public void setVo(UserVo vo) {
        redisUtil.setValueExpire(UserRedisConstant.USER_VO,
                                 vo,
                                 UserRedisConstant.VO_DURATION,
                                 vo.getId());
    }

    public UserVo getVo(Long userId) {
        try {
            return (UserVo) redisUtil.getValue(UserRedisConstant.USER_VO, userId);
        } catch (Exception ignored) {}
        return null;
    }

    public String getAndDeleteAvatarPath(Long userId) {
        try {
            return (String) redisUtil.getValueAndDelete(UserRedisConstant.USER_AVATAR, userId);
        } catch (Exception ignored) {}
        return null;
    }
}
