package com.wolfhouse.springboot3initial.util.verifynode.user;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.EmailVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.util.verifynode.ServiceVerifyConstant;

/**
 * @author Rylin Wolf
 */
public class UserEmailVerifyNode extends EmailVerifyNode {
    private final UserAdminAuthMediator mediator;

    {
        setStrategy(VerifyStrategy.WITH_CUSTOM_EXCEPTION);
        this.exception(ServiceVerifyConstant.ILLEGAL_EMAIL);
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
        // 格式验证 + 查重验证
        if (!super.verify()) {
            return false;
        }
        this.exception(UserConstant.EXIST_EMAIL);
        return !mediator.isUserEmailExist(t);
    }
}
