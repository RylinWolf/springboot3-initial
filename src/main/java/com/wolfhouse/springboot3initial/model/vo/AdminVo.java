package com.wolfhouse.springboot3initial.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wolfhouse.springboot3initial.model.domain.Admin;
import com.wolfhouse.springboot3initial.model.domain.Authentication;
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

    /**
     * 将 Admin 实体对象转换为 AdminVo 数据对象。
     *
     * @param entity 需要转换的 Admin 对象
     * @return 转换后的 AdminVo 对象，其中包含管理员的相关信息。
     */
    public static AdminVo toVo(Admin entity) {
        AdminVo vo = new AdminVo();
        BeanUtil.copyProperties(entity, vo);
        vo.setAuthentication(JSONUtil.toList(entity.getAuthentication(), Authentication.class));
        return vo;
    }

    /**
     * 将 AdminVo 对象转换为 Admin 对象。
     *
     * @return 转换后的 Admin 对象
     */
    public Admin toEntity() {
        Admin admin = new Admin();
        BeanUtil.copyProperties(this, admin);
        admin.setAuthentication(JSONUtil.toJsonStr(this.getAuthentication()));
        return admin;
    }
}
