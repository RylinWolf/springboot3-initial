package com.wolfhouse.springboot3initial.mediator;

import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLoginDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Rylin Wolf
 */
public interface UserAdminAuthMediator {
    /**
     * 检查具有指定ID的用户认证是否存在。
     *
     * @param id 用户认证的唯一标识符
     * @return 如果用户认证存在，返回true；否则返回false
     */
    Boolean isUserExist(Long id);

    /**
     * 检查具有指定电子邮件的用户是否存在。
     *
     * @param email 用户的电子邮件地址
     * @return 如果拥有该电子邮件地址的用户存在，返回true；否则返回false
     */
    Boolean isUserEmailExist(String email);

    /**
     * 注册用户服务组件到中介者。
     *
     * @param userService 用户服务组件实例
     */
    void registerUserService(UserService userService);

    /**
     * 注册认证服务组件到中介者。
     *
     * @param authService 认证服务组件实例
     */
    void registerAuthService(AuthenticationService authService);

    /**
     * 注册管理员服务组件到中介者。
     *
     * @param adminService 管理员服务组件实例
     */
    void registerAdminService(AdminService adminService);

    /**
     * 检查具有指定ID的用户是否是管理员。
     *
     * @param id 用户的唯一标识符
     * @return 如果用户是管理员，返回true；否则返回false
     */
    Boolean isAdmin(Long id);

    /**
     * 检查指定的管理员名称是否存在。
     *
     * @param adminName 管理员名称，用于唯一标识管理员
     * @return 如果管理员名称存在，返回 true；否则返回 false
     */
    Boolean isAdminNameExist(String adminName);

    /**
     * 尝试用户登录。
     *
     * @param userLoginDto 包含登录信息的DTO对象，包括用户名和密码
     * @return 如果登录成功，返回包含用户本地信息的DTO对象；如果登录失败，返回null
     */
    UserLocalDto tryLogin(UserLoginDto userLoginDto);

    /**
     * 获取指定管理员ID的所有权限。
     *
     * @param id 管理员的唯一标识符
     * @return 返回该管理员拥有的所有权限列表
     */
    List<Authentication> getAuthByAdminId(Long id);

    /**
     * 获取当前登录用户信息，如果未登录则抛出异常。
     *
     * @return 返回当前登录用户的本地信息DTO对象
     * @throws ServiceException 如果用户未登录，则抛出异常
     */
    UserLocalDto getLoginOrThrow();

    /**
     * 检查指定的权限ID集合是否都存在。
     *
     * @param t 需要检查的权限ID集合
     * @return 如果所有权限ID都存在返回true，否则返回false
     */
    Boolean areAuthIdsExist(Collection<Long> t);

    /**
     * 更新会话的最后访问时间。
     *
     * @param time 表示最后访问时间的 {@code LocalDateTime} 对象
     * @return 如果更新成功，返回 {@code true}；否则返回 {@code false}
     */
    Boolean updateAccessedTime(LocalDateTime time);
}
