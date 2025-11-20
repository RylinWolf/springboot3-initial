package com.wolfhouse.springboot3initial.util.verifynode.admin;

import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;

import java.util.Collection;

/**
 * @author Rylin Wolf
 */
public class AuthVerifyNode {
    public static AuthIdsExistVerifyNode idsExist(UserAdminAuthMediator mediator, Collection<Long> ids) {
        return new AuthIdsExistVerifyNode(ids, mediator);
    }
}
