package com.wolfhouse.springboot3initial.mvc.mapper.auth;

import com.mybatisflex.core.BaseMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限表 映射层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Mapper
public interface AuthenticationMapper extends BaseMapper<Authentication> {
    /**
     * 根据给定的权限 ID 列表，查询其对应的子权限列表。
     *
     * @param authIds 一个包含权限 ID 的列表，用于指定需要查询子权限的权限。
     * @return 包含所有匹配的子权限的列表，如果没有匹配的子权限，则返回空列表。
     */
    List<Long> getChildIds(List<Long> authIds);
}
