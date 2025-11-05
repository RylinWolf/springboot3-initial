package com.wolfhouse.springboot3initial.common.util.verify.servicenode.common;

/**
 * @author Rylin Wolf
 */
public class ServiceVerifyNode {
    public static PasswordVerifyNode password(String t) {
        return new PasswordVerifyNode(t);
    }

    public static PhoneVerifyNode phone(String t) {
        return new PhoneVerifyNode(t);
    }
}
