package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import lombok.Data;

import java.util.Date;

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
    private Date birth;

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
    private String email;

    /** 密码 */
    private String password;
}
