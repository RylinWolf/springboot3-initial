package com.wolfhouse.springboot3initial.common.util.verify.servicenode.common;

import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.ServiceVerifyConstant;
import com.wolfhouse.springboot3initial.exception.ServiceException;

/**
 * @author Rylin Wolf
 */
public class PhoneVerifyNode extends com.wolfhouse.springboot3initial.common.util.verify.impl.PhoneVerifyNode {
    {
        this.allowNull = true;
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
        this.customException = new ServiceException(ServiceVerifyConstant.ILLEGAL_PHONE);
    }

    public PhoneVerifyNode(String t) {
        this.target(t);
    }
}
