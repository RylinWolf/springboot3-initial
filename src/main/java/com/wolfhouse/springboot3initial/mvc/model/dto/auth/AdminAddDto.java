package com.wolfhouse.springboot3initial.mvc.model.dto.auth;

import lombok.Data;

import java.util.List;

/**
 * @author Rylin Wolf
 */
@Data
public class AdminAddDto {
    private Long userId;
    private String adminName;
    private List<Long> authentication;
}
