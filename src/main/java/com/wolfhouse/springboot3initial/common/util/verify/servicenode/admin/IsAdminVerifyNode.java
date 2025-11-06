package com.wolfhouse.springboot3initial.common.util.verify.servicenode.admin;

import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class IsAdminVerifyNode extends BaseVerifyNode<Long> {
    private final UserAdminAuthMediator mediator;

    {
        this.allowNull = false;
        this.customException = new ServiceException(AuthenticationConstant.NOT_ADMIN);
    }

    public IsAdminVerifyNode(UserAdminAuthMediator mediator) {
        this.mediator = mediator;
    }

    public IsAdminVerifyNode(Long aLong, UserAdminAuthMediator mediator) {
        super(aLong);
        this.mediator = mediator;
    }

    public IsAdminVerifyNode(Long aLong, Boolean allowNull, UserAdminAuthMediator mediator) {
        super(aLong, allowNull);
        this.mediator = mediator;
    }

    @Override
    public boolean verify() {
        if (t == null) {
            return allowNull;
        }

        return mediator.isAdmin(t);
    }
}
