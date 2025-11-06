package com.wolfhouse.springboot3initial.common.util.verify.servicenode.user;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.StrLenVerifyNode;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;

import java.time.LocalDate;

/**
 * @author Rylin Wolf
 */
public class UserVerifyNode {
    public static UserBirthVerifyNode birth(LocalDate target) {
        return (UserBirthVerifyNode) new UserBirthVerifyNode().target(target);
    }

    public static StrLenVerifyNode username(String username) {
        return (StrLenVerifyNode) new StrLenVerifyNode(username)
            .min(2L)
            .max(20L)
            .allowNull(false)
            .setStrategy(VerifyStrategy.WITH_CUSTOM_EXCEPTION)
            .setCustomException(new VerifyException(UserConstant.ILLEGAL_USERNAME));
    }

    public static StrLenVerifyNode status(String status) {
        return (StrLenVerifyNode) new StrLenVerifyNode(status)
            .max(200L)
            .allowNull(true)
            .setStrategy(VerifyStrategy.WITH_CUSTOM_EXCEPTION)
            .setCustomException(new VerifyException(UserConstant.ILLEGAL_STATUS));
    }

    public static GenderVerifyNode gender(Object o) {
        return (GenderVerifyNode) new GenderVerifyNode().from(o)
                                                        .target(GenderEnum.class);
    }

    public static UserEmailVerifyNode email(UserAdminAuthMediator mediator, String email) {
        return new UserEmailVerifyNode(mediator, email);
    }
}
