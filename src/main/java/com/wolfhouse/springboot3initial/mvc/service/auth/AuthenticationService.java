package com.wolfhouse.springboot3initial.mvc.service.auth;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationQueryDto;

import java.util.Collection;
import java.util.List;

/**
 * 权限表 服务层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
public interface AuthenticationService extends IService<Authentication> {
    /**
     * 添加权限。
     *
     * @param dto 包含权限信息的 DTO 对象，包括父权限 ID、权限标识和描述。
     * @return 返回新增的权限对象。
     * @throws Exception 如果添加权限过程发生错误，则抛出异常。
     */
    Authentication addAuth(AuthenticationDto dto) throws Exception;

    // region 查询

    /**
     * 根据条件查询权限信息，返回分页结果。
     *
     * @param dto 包含查询条件的 DTO 对象，包括父权限 ID、权限标识和描述。
     * @return 返回包含权限信息的分页结果对象。
     */
    Page<Authentication> queryBy(AuthenticationQueryDto dto);

    /**
     * 根据指定的权限 ID 集合批量查询权限信息。
     *
     * @param ids 权限 ID 集合，用于标识需要查询的权限。
     * @return 返回包含对应权限信息的列表。如果未找到对应的权限信息，可能返回空列表。
     */
    List<Authentication> getByIds(Collection<Long> ids);

    /**
     * 检查指定权限是否存在。
     *
     * @param id 权限 ID，用于标识需要检查的权限。
     * @return 如果权限存在，返回 true；否则返回 false。
     */
    Boolean isAuthExist(Long id);

    /**
     * 检查指定的权限标识是否存在。
     *
     * @param authCode 权限标识，用于唯一标识权限。
     * @return 如果权限标识存在，返回 true；否则返回 false。
     */
    Boolean isAuthCodeExist(String authCode);

    /**
     * 根据权限 ID 获取权限信息。
     *
     * @param id 权限 ID，用于唯一标识需要查询的权限。
     * @return 返回对应的权限对象。如果未找到对应的权限信息，可能返回 null。
     */
    Authentication getAuthById(Long id);

    /**
     * 根据权限标识获取权限信息。
     *
     * @param authCode 权限标识，用于唯一标识权限。
     * @return 返回对应的权限对象。如果未找到对应的权限信息，可能返回 null。
     */
    Authentication getAuthByCode(String authCode);
    // endregion
}