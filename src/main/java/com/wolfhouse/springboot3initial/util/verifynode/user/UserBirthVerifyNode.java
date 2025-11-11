package com.wolfhouse.springboot3initial.util.verifynode.user;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.BirthVerifyNode;

/**
 * @author Rylin Wolf
 */
public class UserBirthVerifyNode extends BirthVerifyNode {
    {
        this.allowNull = true;
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
        this.customException = new VerifyException(UserConstant.ILLEGAL_BIRTH);
    }
}
