package com.wolfhouse.springboot3initial.mapper.auth;

import com.mybatisflex.core.BaseMapper;
import com.wolfhouse.springboot3initial.model.domain.auth.Authentication;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表 映射层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Mapper
public interface AuthenticationMapper extends BaseMapper<Authentication> {


}
