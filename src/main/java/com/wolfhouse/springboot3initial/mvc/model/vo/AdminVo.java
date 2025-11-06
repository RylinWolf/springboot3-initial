package com.wolfhouse.springboot3initial.mvc.model.vo;

import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import lombok.Data;

import java.util.List;

/**
 * @author Rylin Wolf
 */
@Data
public class AdminVo {
    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 管理权限
     */
    private List<Authentication> authentication;
}
