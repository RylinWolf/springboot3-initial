package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用于记录登录信息的用户 Dto
 *
 * @author Rylin Wolf
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLocalDto implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String account;
    private Boolean isAdmin;
}
