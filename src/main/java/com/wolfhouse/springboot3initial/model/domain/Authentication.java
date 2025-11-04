package com.wolfhouse.springboot3initial.model.domain;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.lang.String;

/**
 * 权限表 实体类。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Data
@Schema(name = "权限表")
@Table(value = "authentication")
public class Authentication {

    /**
     * 权限 ID
     */
    @Schema(description = "权限 ID")
    @Id(keyType = KeyType.Generator)
    private Long id;

    /**
     * 父权限 ID
     */
    @Schema(description = "父权限 ID")
    @Column(value = "parent_id")
    private Long parentId;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    @Column(value = "code")
    private String code;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;


}
