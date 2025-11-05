package com.wolfhouse.springboot3initial.common.util.verify.impl;

/**
 * @author Rylin Wolf
 */
public class StrLenVerifyNode extends BaseVerifyNode<String> {
    private Long min;
    private Long max;

    public StrLenVerifyNode() {
        super();
    }

    public StrLenVerifyNode(String s) {
        super(s);
    }

    public StrLenVerifyNode(String s, Boolean allowNull) {
        super(s, allowNull);
    }

    public StrLenVerifyNode min(Long min) {
        this.min = min;
        return this;
    }

    public StrLenVerifyNode max(Long max) {
        this.max = max;
        return this;
    }

    @Override
    public boolean verify() {
        if (t == null) {
            return allowNull;
        }

        return t.length() >= min && t.length() <= max;
    }
}
