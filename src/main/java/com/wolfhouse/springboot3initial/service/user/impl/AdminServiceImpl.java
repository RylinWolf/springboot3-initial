package com.wolfhouse.springboot3initial.service.user.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.mapper.AdminMapper;
import com.wolfhouse.springboot3initial.model.domain.Admin;
import com.wolfhouse.springboot3initial.service.IAdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}