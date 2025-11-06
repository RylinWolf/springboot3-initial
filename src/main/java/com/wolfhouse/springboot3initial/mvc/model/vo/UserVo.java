package com.wolfhouse.springboot3initial.mvc.model.vo;

import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private GenderEnum gender;

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
    private String email;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 注册时间
     */
    private LocalDateTime registerDate;
}
