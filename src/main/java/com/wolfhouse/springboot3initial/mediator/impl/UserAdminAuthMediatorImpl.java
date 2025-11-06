package com.wolfhouse.springboot3initial.mediator.impl;

import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import org.springframework.stereotype.Component;

/**
 * @author Rylin Wolf
 */
@Component
public class UserAdminAuthMediatorImpl implements UserAdminAuthMediator {
    private UserService userService;
    private AuthenticationService authService;
    private AdminService adminService;

    @Override
    public Boolean isUserExist(Long id) {
        return userService.isUserExist(id);
    }

    @Override
    public Boolean isUserEmailExist(String email) {
        return userService.isUserEmailExist(email);
    }

    @Override
    public Boolean isAdmin(Long id) {
        return adminService.isAdmin(id);
    }

    @Override
    public Boolean isAdminNameExist(String adminName) {
        return adminService.isAdminNameExist(adminName);
    }

    // region 注册服务

    @Override
    public void registerUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public void registerAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    // endregion
}
