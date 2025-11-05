package com.wolfhouse.springboot3initial.mvc.service.auth.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.mvc.mapper.auth.AuthenticationMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import org.springframework.stereotype.Service;

/**
 * 权限表 服务层实现。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Service
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {

}