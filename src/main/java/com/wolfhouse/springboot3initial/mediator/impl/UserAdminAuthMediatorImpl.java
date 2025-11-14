package com.wolfhouse.springboot3initial.mediator.impl;

import cn.hutool.json.JSONUtil;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLoginDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Rylin Wolf
 */
@Component
public class UserAdminAuthMediatorImpl implements UserAdminAuthMediator {
    private UserService userService;
    private AuthenticationService authService;
    private AdminService adminService;

    /**
     * 检查用户是否存在
     *
     * @param id 用户ID
     * @return 用户是否存在
     */
    @Override
    public Boolean isUserExist(Long id) {
        return userService.isUserExist(id);
    }

    /**
     * 检查用户邮箱是否存在
     *
     * @param email 邮箱地址
     * @return 邮箱是否已被使用
     */
    @Override
    public Boolean isUserEmailExist(String email) {
        // 邮箱判断重复统一使用小写
        return userService.isUserEmailExist(email.toLowerCase());
    }

    /**
     * 检查用户是否为管理员
     *
     * @param id 用户ID
     * @return 是否为管理员
     */
    @Override
    public Boolean isAdmin(Long id) {
        return adminService.isAdmin(id);
    }

    /**
     * 检查管理员用户名是否存在
     *
     * @param adminName 管理员用户名
     * @return 用户名是否已存在
     */
    @Override
    public Boolean isAdminNameExist(String adminName) {
        return adminService.isAdminNameExist(adminName);
    }

    /**
     * 尝试用户登录
     *
     * @param userLoginDto 登录信息传输对象
     * @return 本地用户信息传输对象，登录失败返回null
     */
    @Override
    public UserLocalDto tryLogin(UserLoginDto userLoginDto) {
        String certification = userLoginDto.getCertification();
        String password = userLoginDto.getPassword();
        // 检查参数
        ThrowUtil.throwOnCondition(BeanUtil.isAnyBlank(certification, password),
                                   HttpCode.PARAM_ERROR.message);

        // 验证用户名密码是否匹配
        if (!userService.verify(certification, password)) {
            return null;
        }
        // 获取登录用户
        User user = userService.getByCertificate(certification);
        // 获取管理员信息并保存权限
        Admin admin = adminService.getById(user.getId());
        // 创建本地登录用户实例
        UserLocalDto localDto = BeanUtil.copyProperties(user, UserLocalDto.class);
        localDto.setIsAdmin(false);
        if (admin != null) {
            localDto.setIsAdmin(true);
        }
        return localDto;
    }

    /**
     * 获取管理员的权限列表
     *
     * @param id 管理员ID
     * @return 权限列表
     */
    @Override
    public List<Authentication> getAuthByAdminId(Long id) {
        // 获取管理员的权限
        List<Long> authIds = JSONUtil.toList(adminService.getById(id)
                                                         .getAuthentication(), Long.class);
        // 获取权限及其子权限
        return authService.getByIdsWithChild(authIds);
    }

    /**
     * 获取当前登录用户信息，如果未登录则抛出异常
     *
     * @return 当前登录用户信息
     */
    @Override
    public UserLocalDto getLoginOrThrow() {
        return userService.getLoginOrThrow();
    }

    /**
     * 检查权限ID集合是否都存在
     *
     * @param ids 权限ID集合
     * @return 是否都存在
     */
    @Override
    public Boolean areAuthIdsExist(Collection<Long> ids) {
        return authService.areAuthIdsExist(ids);
    }

    /**
     * 更新用户访问时间
     *
     * @param time 访问时间
     * @return 更新是否成功
     */
    @Override
    public Boolean updateAccessedTime(LocalDateTime time) {
        User user = userService.getById(getLoginOrThrow().getId());
        user.setLoginDate(time);
        return userService.updateById(user);
    }

    // region 注册服务

    /**
     * 注册用户服务
     *
     * @param userService 用户服务实例
     */
    @Override
    public void registerUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册认证服务
     *
     * @param authService 认证服务实例
     */
    @Override
    public void registerAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    /**
     * 注册管理员服务
     *
     * @param adminService 管理员服务实例
     */
    @Override
    public void registerAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    // endregion
}
