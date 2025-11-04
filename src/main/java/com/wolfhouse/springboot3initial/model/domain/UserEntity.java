package com.wolfhouse.springboot3initial.model.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 用户表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "user")
public class UserEntity {

    /**
     * 用户 ID
     */
    @Id(keyType = KeyType.Generator)
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
    private Date birth;

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
    private Date loginDate;

    /**
     * 注册时间
     */
    @Column(value = "register_date")
    private Date registerDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPersonalStatus() {
        return personalStatus;
    }

    public void setPersonalStatus(String personalStatus) {
        this.personalStatus = personalStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
