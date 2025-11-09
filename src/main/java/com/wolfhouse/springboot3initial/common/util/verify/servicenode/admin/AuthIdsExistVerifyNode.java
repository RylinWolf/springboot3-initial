package com.wolfhouse.springboot3initial.common.util.verify.servicenode.admin;

import com.wolfhouse.springboot3initial.common.util.verify.impl.BaseVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

import java.util.Collection;

/**
 * @author Rylin Wolf
 */
public class AuthIdsExistVerifyNode extends BaseVerifyNode<Collection<Long>> {
    private final UserAdminAuthMediator mediator;

    public AuthIdsExistVerifyNode(UserAdminAuthMediator mediator) {
        this.mediator = mediator;
    }

    public AuthIdsExistVerifyNode(Collection<Long> longs, UserAdminAuthMediator mediator) {
        super(longs);
        this.mediator = mediator;
    }

    public AuthIdsExistVerifyNode(Collection<Long> longs, Boolean allowNull, UserAdminAuthMediator mediator) {
        super(longs, allowNull);
        this.mediator = mediator;
    }

    @Override
    public boolean verify() {
        if (t == null || t.isEmpty()) {
            return allowNull;
        }

        return mediator.areAuthIdsExist(t);
    }
}
