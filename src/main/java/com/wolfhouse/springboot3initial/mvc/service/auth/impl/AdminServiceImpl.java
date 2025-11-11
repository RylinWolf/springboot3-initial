package com.wolfhouse.springboot3initial.mvc.service.auth.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyTool;
import com.wolfhouse.springboot3initial.common.util.verify.impl.EmptyVerifyNode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.mapper.auth.AdminMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AdminAddDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import com.wolfhouse.springboot3initial.security.PermissionConstant;
import com.wolfhouse.springboot3initial.security.PermissionService;
import com.wolfhouse.springboot3initial.util.verifynode.admin.AdminVerifyNode;
import com.wolfhouse.springboot3initial.util.verifynode.admin.AuthVerifyNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private final UserAdminAuthMediator mediator;

    // region 初始化

    @PostConstruct
    public void init() {
        this.mediator.registerAdminService(this);
    }
    // endregion

    // region 查询存在

    @Override
    public Boolean isAdmin(Long userId) {
        // 参数校验
        ThrowUtil.throwOnCondition(userId == null || userId <= 0,
                                   HttpCode.PARAM_ERROR.code,
                                   HttpCode.PARAM_ERROR.message);

        return exists(QueryWrapper.create()
                                  .eq(Admin::getUserId, userId, true));
    }

    @Override
    public Boolean isAdminNameExist(String adminName) {
        return exists(QueryWrapper.create()
                                  .from(Admin.class)
                                  .eq(Admin::getAdminName, adminName, true));
    }
    // endregion

    @Override
    public Admin addAdmin(AdminAddDto dto) {
        // 1. 检查参数
        // 检查权限
        if (!PermissionService.hasPerm(PermissionConstant.ADMIN_ADD)) {
            // 无权限
            throw new ServiceException(HttpCode.NO_PERMISSION);
        }
        // 检查参数
        VerifyTool.of(
                      // 用户是否存在
                      EmptyVerifyNode.of(dto.getUserId())
                                     .predicate(mediator::isUserExist)
                                     .exception(new ServiceException(HttpCode.PARAM_ERROR,
                                                                     UserConstant.USER_NOT_EXIST)),
                      // 用户是否已经是管理员
                      AdminVerifyNode.isAdmin(mediator, true)
                                     .target(dto.getUserId())
                                     .exception(new ServiceException(HttpCode.BAD_REQUEST,
                                                                     AuthenticationConstant.ADMIN_ALREADY_EXIST)),
                      // 管理员名称是否已存在
                      AdminVerifyNode.nameExist(mediator, dto.getAdminName())
                                     .exception(new ServiceException(HttpCode.PARAM_ERROR.code,
                                                                     AuthenticationConstant.ADMIN_NAME_EXIST)),
                      // 权限是否存在
                      AuthVerifyNode.idsExist(mediator, dto.getAuthentication())
                     )
                  .doVerify();

        // 2. 添加管理员
        Admin admin = BeanUtil.copyProperties(dto, Admin.class);
        // 添加失败则抛出异常
        ThrowUtil.throwOnCondition(!save(admin),
                                   HttpCode.SQL_ERROR.code,
                                   HttpCode.SQL_ERROR.message);

        // 3. 返回管理员
        return getById(admin.getUserId());
    }

}