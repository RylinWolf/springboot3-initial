package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import lombok.Data;

/**
 * @author Rylin Wolf
 */
@Data
public class UserPwdUpdateDto {
    private String oldPassword;
    private String newPassword;
}
