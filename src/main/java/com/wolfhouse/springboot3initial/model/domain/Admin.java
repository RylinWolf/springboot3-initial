package com.wolfhouse.springboot3initial.model.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

/**
 * 管理员表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "admin")
@Data
public class Admin {

    /**
     * 用户 ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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

    /**
     * 逻辑删除
     */
    @Column(isLogicDelete = true)
    private Boolean isDeleted;
}
