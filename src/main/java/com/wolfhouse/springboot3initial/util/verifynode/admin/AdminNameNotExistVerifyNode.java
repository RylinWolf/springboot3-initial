package com.wolfhouse.springboot3initial.util.verifynode.admin;

import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class AdminNameNotExistVerifyNode extends BaseVerifyNode<String> {
    private final UserAdminAuthMediator mediator;

    {
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
        this.customException = new VerifyException(AuthenticationConstant.ADMIN_NAME_EXIST);
    }

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
