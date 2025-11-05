package com.wolfhouse.springboot3initial.mvc.model.dto.user;

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
    private JsonNullable<String> username;

    /**
     * 头像
     */
    private JsonNullable<String> avatar;

    /**
     * 性别
     */
    private JsonNullable<Integer> gender;

    /**
     * 生日
     */
    private JsonNullable<LocalDate> birth;

    /**
     * 个性标签
     */
    private JsonNullable<String> personalStatus;

    /**
     * 手机
     */
    private JsonNullable<String> phone;

    /**
     * 邮箱
     */
    @Email
    private JsonNullable<String> email;
}
