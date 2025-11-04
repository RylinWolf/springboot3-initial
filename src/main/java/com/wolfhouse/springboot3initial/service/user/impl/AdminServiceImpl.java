package com.wolfhouse.springboot3initial.service.user.impl;


import org.springframework.stereotype.Service;
import com.wolfhouse.springboot3initial.service.IAdminService;
import com.wolfhouse.springboot3initial.model.domain.AdminEntity;
import com.wolfhouse.springboot3initial.mapper.AdminMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 管理员表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminEntity> implements IAdminService {

}