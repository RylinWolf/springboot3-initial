package com.wolfhouse.springboot3initial.common.util.verify.servicenode.admin;

import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class AdminNameNotExistVerifyNode extends BaseVerifyNode<String> {
    private final UserAdminAuthMediator mediator;

    public AdminNameNotExistVerifyNode(UserAdminAuthMediator mediator) {
        this.mediator = mediator;
    }

    public AdminNameNotExistVerifyNode(String s, UserAdminAuthMediator mediator) {
        super(s);
        this.mediator = mediator;
    }

    public AdminNameNotExistVerifyNode(String s, Boolean allowNull, UserAdminAuthMediator mediator) {
        super(s, allowNull);
        this.mediator = mediator;
    }

    @Override
    public boolean verify() {
        if (t == null) {
            return allowNull;
        }

        return !mediator.isAdminNameExist(t);
    }
}
