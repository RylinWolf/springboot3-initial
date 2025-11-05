package com.wolfhouse.springboot3initial.mvc.service.user;


import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;

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
     * @return 创建后的用户视图对象，包含 id、帐号
     */
    UserVo register(UserRegisterDto dto);

    /**
     * 根据用户 ID 获取对应的用户视图对象。
     *
     * @param id 用户 ID，唯一标识一个用户
     * @return 用户视图对象，包含用户的详细信息；如果用户不存在，则返回 null
     */
    UserVo getVoById(Long id);

}