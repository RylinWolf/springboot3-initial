package com.wolfhouse.springboot3initial.mvc.service.auth;


import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AdminAddDto;

/**
 * 管理员表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface AdminService extends IService<Admin> {
    /**
     * 添加管理员。
     *
     * @param dto 管理员添加 dto
     * @return 返回新增的管理员对象。
     */
    Admin addAdmin(AdminAddDto dto) throws Exception;

    /**
     * 检查指定用户是否具有管理员身份。
     *
     * @param userId 用户 ID，用于标识需要检查的用户。
     * @return 如果用户是管理员返回 true，否则返回 false。
     */
    Boolean isAdmin(Long userId);

    /**
     * 检查指定的管理员名称是否存在。
     *
     * @param adminName 管理员名称，用于唯一标识管理员
     * @return 如果管理员名称存在，返回 true；否则返回 false
     */
    Boolean isAdminNameExist(String adminName);
}