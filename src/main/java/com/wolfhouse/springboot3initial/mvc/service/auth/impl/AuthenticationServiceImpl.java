package com.wolfhouse.springboot3initial.mvc.service.auth.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mvc.mapper.auth.AuthenticationMapper;
import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationQueryDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import com.wolfhouse.springboot3initial.security.PermissionConstant;
import com.wolfhouse.springboot3initial.security.PermissionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 权限表 服务层实现。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {
    private final UserAdminAuthMediator mediator;

    // region 初始化

    @PostConstruct
    public void init() {
        this.mediator.registerAuthService(this);
    }
    // endregion

    // region 查询


    @Override
    public List<Authentication> getByIds(Collection<Long> ids) {
        return listByIds(ids);
    }

    @Override
    public List<Authentication> getByCodes(Collection<String> codes) {
        return list(QueryWrapper.create()
                                .in(Authentication::getCode, codes));
    }

    @Override
    public Page<Authentication> queryBy(AuthenticationQueryDto dto) {


        QueryWrapper wrapper = QueryWrapper.create()
                                           .from(Authentication.class);
        // 权限标识
        dto.getCode()
           .ifPresent(c -> wrapper.like(Authentication::getCode, c));
        // 父权限 可查询空，空时为顶级权限
        dto.getParentId()
           .ifPresent(p -> wrapper.eq(Authentication::getParentId, p, true));
        // 权限描述
        dto.getDescription()
           .ifPresent(d -> wrapper.like(Authentication::getDescription, d));
        return mapper.paginate(dto.getPageNum(), dto.getPageSize(), wrapper);
    }

    @Override
    public Boolean isAuthExist(Long id) {
        return exists(QueryWrapper.create()
                                  .eq(Authentication::getId, id));
    }

    @Override
    public Boolean isAuthCodeExist(String authCode) {
        return exists(QueryWrapper.create()
                                  .eq(Authentication::getCode, authCode));
    }

    @Override
    public Authentication getAuthById(Long id) {
        return getById(id);
    }

    @Override
    public Authentication getAuthByCode(String authCode) {
        return getOne(QueryWrapper.create()
                                  .eq(Authentication::getCode, authCode));
    }

    @Override
    public List<Authentication> getByIdsWithChild(List<Long> authIds) {
        if (authIds == null || authIds.isEmpty()) {
            return Collections.emptyList();
        }
        // 获取所有权限
        List<Authentication> auths = getByIds(authIds);
        List<Long> childIds = mapper.getChildIds(authIds);
        // 递归获取所有子权限
        auths.addAll(getByIdsWithChild(childIds));
        return auths;
    }

    /**
     * 检查指定的权限 ID 集合是否存在。通过比较实际存在的权限数量与输入 ID 集合的大小来判断。
     *
     * @param ids 需要检查的权限 ID 集合
     * @return 如果所有指定的权限 ID 都存在，返回 true；如果有任何一个权限 ID 不存在，返回 false
     */
    @Override
    public Boolean areAuthIdsExist(Collection<Long> ids) {
        long count = count(QueryWrapper.create()
                                       .from(Authentication.class)
                                       .in(Authentication::getId, ids));
        return count == ids.size();
    }

    // endregion

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Authentication addAuth(AuthenticationDto dto) throws Exception {
        // 0. 检查权限
        ThrowUtil.throwOnCondition(!PermissionService.hasPerm(PermissionConstant.AUTH_ADD),
                                   HttpCode.NO_PERMISSION.code,
                                   HttpCode.NO_PERMISSION.message);

        // 1. 参数校验
        // 权限标识已存在或父 ID 不存在则抛出异常
        ThrowUtil.throwOnCondition((isAuthCodeExist(dto.getCode()) || !isAuthExist(dto.getParentId())),
                                   HttpCode.PARAM_ERROR.code,
                                   AuthenticationConstant.AUTH_CODE_EXIST);
        // 2. 添加权限
        Authentication auth = new Authentication(null,
                                                 dto.getParentId(),
                                                 dto.getCode(),
                                                 dto.getDescription());
        // 保存失败或权限 ID 为空，抛出异常
        if (save(auth) || auth.getId() == null) {
            log.error("权限保存失败, {}", dto);
            throw new ServiceException(HttpCode.UNKNOWN);
        }

        // 3. 返回权限
        return auth;
    }
}