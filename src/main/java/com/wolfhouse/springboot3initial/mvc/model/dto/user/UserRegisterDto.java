package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Rylin Wolf
 */
@Data
public class UserRegisterDto {
    /**
     * 用户名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birth;

    /**
     * 个性标签
     */
    private String personalStatus;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /** 密码 */
    private String password;
}
