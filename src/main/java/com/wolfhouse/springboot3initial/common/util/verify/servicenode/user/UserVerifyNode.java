package com.wolfhouse.springboot3initial.common.util.verify.servicenode.user;

import java.time.LocalDate;

/**
 * @author Rylin Wolf
 */
public class UserVerifyNode {
    public static UserBirthVerifyNode birth(LocalDate target) {
        return (UserBirthVerifyNode) new UserBirthVerifyNode().target(target);
    }
}
