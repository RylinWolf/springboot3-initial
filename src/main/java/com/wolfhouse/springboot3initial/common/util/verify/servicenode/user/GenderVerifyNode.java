package com.wolfhouse.springboot3initial.common.util.verify.servicenode.user;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.EnumVerifyNode;

/**
 * 性别验证节点
 *
 * @author Rylin Wolf
 */
public class GenderVerifyNode extends EnumVerifyNode {
    {
        this.allowNull = true;
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
        this.customException = new VerifyException(UserConstant.ILLEGAL_GENDER);
    }
}
