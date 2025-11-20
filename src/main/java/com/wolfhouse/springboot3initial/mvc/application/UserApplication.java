package com.wolfhouse.springboot3initial.mvc.application;

import com.wolfhouse.springboot3initial.config.objectmapper.JacksonObjectMapper;
import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.security.SecurityContextUtil;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisProperties;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.service.RedisUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
    private final RedisUserService redisUserService;
    private final UserAdminAuthMediator mediator;
    private final ServiceRedisProperties properties;
    private JacksonObjectMapper redisObjectMapper;

    @Autowired
    public void setRedisObjectMapper(@Qualifier("redisObjectMapper") JacksonObjectMapper redisObjectMapper) {
        this.redisObjectMapper = redisObjectMapper;
    }

    public UserVo getLoginVo() {
        // 获得登录对象并查询
        UserLocalDto login = SecurityContextUtil.getLoginUser();
        if (login == null) {
            return null;
        }
        return getVoById(login.getId());
    }

    public UserVo getVoById(Long id) {
        // 1. 获得 Vo
        // 从缓存获取
        UserVo vo = redisUserService.getVo(id);
        if (vo == null) {
            // 从数据库获得
            vo = mediator.getVoById(id);
        }
        // 2. 数据聚合
        LocalDateTime loginTime = redisUserService.lastLogin(id);

        if (loginTime == null) {
            loginTime = LocalDateTime.now();
        }
        vo.setLoginDate(loginTime);
        // 3. 缓存
        redisUserService.setVo(vo);

        // 4. 返回
        return vo;
    }
}
