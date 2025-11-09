package com.wolfhouse.springboot3initial.common.util.verify.servicenode.admin;

import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class AdminVerifyNode {
    public static IsAdminVerifyNode isAdmin(UserAdminAuthMediator mediator, Boolean reversed) {
        return new IsAdminVerifyNode(mediator, reversed);
    }

    public static AdminNameNotExistVerifyNode nameExist(UserAdminAuthMediator mediator, String adminName) {
        return new AdminNameNotExistVerifyNode(adminName, mediator);
    }
}
