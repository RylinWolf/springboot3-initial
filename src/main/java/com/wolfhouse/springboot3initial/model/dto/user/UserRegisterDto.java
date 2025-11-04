package com.wolfhouse.springboot3initial.model.dto.user;

import com.mybatisflex.annotation.Column;

import java.util.Date;

/**
 * @author Rylin Wolf
 */
public class UserRegisterDto {
    /**
     * 用户名称
     */
    @Column(value = "username")
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
}
