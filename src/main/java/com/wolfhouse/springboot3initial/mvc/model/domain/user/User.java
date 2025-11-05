package com.wolfhouse.springboot3initial.mvc.model.domain.user;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "user")
@Data
public class User {

    /**
     * 用户 ID
     * 根据密码 ID 手动设置
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 用户名称
     */
    @Column(value = "username")
    private String username;

    /**
     * 帐号
     */
    @Column(value = "account")
    private String account;

    /**
     * 头像
     */
    @Column(value = "avatar")
    private String avatar;

    /**
     * 性别
     */
    @Column(value = "gender")
    private Integer gender;

    /**
     * 生日
     */
    @Column(value = "birth")
    private LocalDate birth;

    /**
     * 个性标签
     */
    @Column(value = "personal_status")
    private String personalStatus;

    /**
     * 手机
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(value = "email")
    private String email;

    /**
     * 最后登录时间
     */
    @Column(value = "login_date")
    private LocalDateTime loginDate;

    /**
     * 注册时间
     */
    @Column(value = "register_date")
    private LocalDateTime registerDate;

    /**
     * 逻辑删除
     */
    @Column(isLogicDelete = true)
    private Boolean isDeleted;
}
