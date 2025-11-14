package com.wolfhouse.springboot3initial.mvc.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.OssUploadLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 映射层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Mapper
public interface OssUploadLogMapper extends BaseMapper<OssUploadLog> {


}
