package com.wolfhouse.springboot3initial.service.user;


import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.model.domain.user.User;
import com.wolfhouse.springboot3initial.model.dto.user.UserRegisterDto;

/**
 * 用户表 服务层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
public interface UserService extends IService<User> {
    /**
     * 创建用户。根据用户名称自动生成帐号并注入。
     * 返回创建后的用户对象。
     *
     * @param dto 用户 dto
     * @return 创建后的用户对象，包含 id、帐号
     */
    User register(UserRegisterDto dto);

}