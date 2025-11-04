package com.wolfhouse.springboot3initial.service.user.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.mapper.user.UserMapper;
import com.wolfhouse.springboot3initial.model.domain.user.User;
import com.wolfhouse.springboot3initial.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Rylin Wolf
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User register(UserRegisterDto dto) {
        return null;
    }
}
