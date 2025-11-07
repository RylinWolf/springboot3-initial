package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于记录登录信息的用户 Dto
 *
 * @author Rylin Wolf
 */
@Data
public class UserLocalDto implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String account;
    private Boolean isAdmin;
}
