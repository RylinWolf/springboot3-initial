package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rylin Wolf
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    @NotNull
    private String certification;
    @NotNull
    private String password;
}
