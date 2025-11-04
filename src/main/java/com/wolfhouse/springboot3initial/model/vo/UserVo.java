package com.wolfhouse.springboot3initial.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Rylin Wolf
 */
@Data
public class UserVo {
    /**
     * 用户 ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 帐号
     */
    private String account;

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

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 注册时间
     */
    private Date registerDate;
}
