package com.wolfhouse.springboot3initial.util.verifynode.common;

import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.impl.StrLenVerifyNode;
import com.wolfhouse.springboot3initial.util.verifynode.ServiceVerifyConstant;

/**
 * 密码验证节点
 *
 * @author Rylin Wolf
 */
public class PasswordVerifyNode extends StrLenVerifyNode {
    {
        this.allowNull = false;
        this.strategy = VerifyStrategy.WITH_CUSTOM_EXCEPTION;
        this.customException = new VerifyException(ServiceVerifyConstant.ILLEGAL_PASSWORD);
        min(8L).max(20L);
    }

    public PasswordVerifyNode(String target) {
        this.target(target);
    }
}
