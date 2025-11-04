package com.wolfhouse.springboot3initial.service.auth.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.mapper.auth.AdminMapper;
import com.wolfhouse.springboot3initial.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.service.auth.AdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}