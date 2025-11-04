package com.wolfhouse.springboot3initial.service.user.impl;


import org.springframework.stereotype.Service;
import com.wolfhouse.springboot3initial.service.AuthenticationService;
import com.wolfhouse.springboot3initial.model.domain.Authentication;
import com.wolfhouse.springboot3initial.mapper.AuthenticationMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 权限表 服务层实现。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Service
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {

}