package com.wolfhouse.springboot3initial.common.util.verify.servicenode.user;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class UserEmailVerifyNode extends BaseVerifyNode<String> {
    private final UserAdminAuthMediator mediator;

    {
        setStrategy(VerifyStrategy.WITH_CUSTOM_EXCEPTION);
        setCustomException(new VerifyException(UserConstant.EXIST_EMAIL));
    }

    public UserEmailVerifyNode(UserAdminAuthMediator mediator, String email) {
        super(email);
        this.mediator = mediator;
    }

    @Override
    public boolean verify() {
        if (t == null) {
            return allowNull;
        }
        return !mediator.isUserEmailExist(t);
    }
}
