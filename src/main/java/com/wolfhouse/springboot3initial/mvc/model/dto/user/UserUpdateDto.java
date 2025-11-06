package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

/**
 * @author Rylin Wolf
 */
@Data
public class UserUpdateDto {
    /**
     * 用户名称
     */
    private JsonNullable<String> username = JsonNullable.undefined();

    /**
     * 头像
     */
    private JsonNullable<String> avatar = JsonNullable.undefined();

    /**
     * 性别
     */
    private JsonNullable<GenderEnum> gender = JsonNullable.undefined();

    /**
     * 生日
     */
    private JsonNullable<LocalDate> birth = JsonNullable.undefined();

    /**
     * 个性标签
     */
    private JsonNullable<String> personalStatus = JsonNullable.undefined();

    /**
     * 手机
     */
    private JsonNullable<String> phone = JsonNullable.undefined();

    /**
     * 邮箱
     */
    @Email
    private JsonNullable<String> email = JsonNullable.undefined();
}
