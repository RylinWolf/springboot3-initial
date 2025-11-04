package com.wolfhouse.springboot3initial.model.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.lang.String;

/**
 * 管理员表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "admin")
public class AdminEntity {

    /**
     * 用户 ID
     */
    @Id(keyType = KeyType.Generator)
    private Long userId;

    /**
     * 管理员名称
     */
    @Column(value = "admin_name")
    private String adminName;

    /**
     * 管理权限
     */
    @Column(value = "authentication")
    private String authentication;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
