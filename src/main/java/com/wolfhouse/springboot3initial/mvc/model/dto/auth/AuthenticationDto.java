package com.wolfhouse.springboot3initial.mvc.model.dto.auth;

import lombok.Data;

/**
 * @author Rylin Wolf
 */
@Data
public class AuthenticationDto {
    /** 父权限 ID */
    private Long parentId;
    /** 权限标识 */
    private String code;
    /** 描述 */
    private String description;
}
