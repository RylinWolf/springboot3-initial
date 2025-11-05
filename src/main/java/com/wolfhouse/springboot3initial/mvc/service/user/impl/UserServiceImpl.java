package com.wolfhouse.springboot3initial.mvc.service.user.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Rylin Wolf
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVo register(UserRegisterDto dto) {
        // TODO 实现注册
        // 1. 校验参数

        // 2. 保存密码，获得密码 ID

        // 3. 保存用户

        // 4. 返回 vo
        return null;
    }

    @Override
    public UserVo getVoById(Long id) {
        return null;
    }
}
