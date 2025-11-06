package com.wolfhouse.springboot3initial.mvc.model.dto.auth;

import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import lombok.Data;

import java.util.List;

/**
 * @author Rylin Wolf
 */
@Data
public class AdminAddDto {
    private Long userId;
    private String adminName;
    private List<Authentication> authentication;
}
