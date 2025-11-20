package com.wolfhouse.springboot3initial.util.verifynode.admin;

import com.wolfhouse.springboot3initial.common.constant.AuthenticationConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;

/**
 * @author Rylin Wolf
 */
public class IsAdminVerifyNode extends BaseVerifyNode<Long> {
    private final UserAdminAuthMediator mediator;
    private final Boolean reversed;

    {
        this.allowNull = false;
        this.customException = new VerifyException(AuthenticationConstant.NOT_ADMIN);
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
    }

    public IsAdminVerifyNode(UserAdminAuthMediator mediator, Boolean reversed) {
        this.mediator = mediator;
        this.reversed = reversed;
    }

    public IsAdminVerifyNode(Long aLong, UserAdminAuthMediator mediator, Boolean reversed) {
        super(aLong);
        this.mediator = mediator;
        this.reversed = reversed;

    }

    public IsAdminVerifyNode(Long aLong, Boolean allowNull, UserAdminAuthMediator mediator, Boolean reversed) {
        super(aLong, allowNull);
        this.mediator = mediator;
        this.reversed = reversed;

    }

    @Override
    public boolean verify() {
        if (t == null) {
            return allowNull;
        }
        Boolean result = mediator.isAdmin(t);
        // 若不反转，则返回结果本身：reversed = false,  result = true, !reversed.equals(result) = true
        return !reversed.equals(result);
    }
}
