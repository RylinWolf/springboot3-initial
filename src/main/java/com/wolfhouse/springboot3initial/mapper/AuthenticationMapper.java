package com.wolfhouse.springboot3initial.mapper;

import com.wolfhouse.springboot3initial.model.domain.Authentication;
import com.mybatisflex.core.BaseMapper;
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
