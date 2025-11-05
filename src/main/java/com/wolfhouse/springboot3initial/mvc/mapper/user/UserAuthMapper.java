package com.wolfhouse.springboot3initial.mvc.mapper.user;

import com.mybatisflex.core.BaseMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证信息表 映射层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {


}
