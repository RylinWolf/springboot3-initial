package com.wolfhouse.springboot3initial.mvc.application;

import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisProperties;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.UserRedisConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 用户的 BFF 层，用于数据聚合
 *
 * @author Rylin Wolf
 */
@Component
@RequiredArgsConstructor
public class UserApplication {
    private final ServiceRedisUtil redisUtil;
    private final UserAdminAuthMediator mediator;
    private final ServiceRedisProperties properties;

    public UserVo getVoById() {
        // 获取登录信息

        // 1. 获得登录对象
        UserLocalDto login = mediator.getLoginOrThrow();
        // 构建 Redis 键
        String key = UserRedisConstant.USER_VO_WITH_FORMAT.formatted(login.getId());

        UserVo vo;
        if (redisUtil.hasKey(key)) {
            // 从缓存获得
            vo = (UserVo) redisUtil.getValue(key);
        } else {
            // 从数据库获得
            vo = mediator.getVoById(login.getId());
        }
        // 2. 数据聚合
        LocalDateTime loginTime = (LocalDateTime) redisUtil.getValue(UserRedisConstant.LAST_LOGIN_WITH_FORMAT,
                                                                     login.getId());
        vo.setLoginDate(loginTime);
        // 3. 缓存
        redisUtil.setValueExpire(key, vo, Duration.ofMinutes(properties.loginExpire()));

        // 4. 返回
        return vo;
    }
}
