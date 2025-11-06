package com.wolfhouse.springboot3initial.common.enums.user;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;

/**
 * @author Rylin Wolf
 */
public enum GenderEnum {
    /** 男性 */
    FEMALE(0, "女"),
    /** 女性 */
    MALE(1, "男"),
    /** 其他 */
    OTHER(2, "其他"),
    ;

    @EnumValue
    public final Integer code;
    @JsonValue
    public final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
