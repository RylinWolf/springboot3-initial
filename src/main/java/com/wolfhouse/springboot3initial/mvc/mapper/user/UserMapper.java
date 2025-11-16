package com.wolfhouse.springboot3initial.mvc.mapper.user;

import com.mybatisflex.core.BaseMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据提供的用户ID与最近登陆时间的映射更新相应用户的访问时间。
     *
     * @param lastLogins 一个包含用户ID为键、最后访问时间为值的映射表
     * @return 更新成功的记录数量
     */
    Long updateAccessedTimeByMap(@Param("lastLogins") Map<Long, LocalDateTime> lastLogins);
}
