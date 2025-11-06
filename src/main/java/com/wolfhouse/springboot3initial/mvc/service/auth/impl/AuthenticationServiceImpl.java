package com.wolfhouse.springboot3initial.mvc.service.auth.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.mvc.mapper.auth.AuthenticationMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationQueryDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 权限表 服务层实现。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Service
public class AuthenticationServiceImpl extends ServiceImpl<AuthenticationMapper, Authentication> implements AuthenticationService {

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

    // endregion

    @Override
    public Authentication addAuth(AuthenticationDto dto) throws Exception {
        // 0. 检查权限
        // TODO 检查当前权限是否可添加权限
        // 1. 参数校验
        ThrowUtil.throwOnCondition(isAuthCodeExist(dto.getCode()),
                                   HttpCode.PARAM_ERROR.code,
                                   AuthenticationConstant.AUTH_CODE_EXIST);
        // 2. 添加权限


        // 3. 返回权限
        return null;
    }
}