package com.wolfhouse.springboot3initial.mvc.service;


import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.mvc.model.domain.OssUploadLog;

import java.util.Set;

/**
 * 服务层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
public interface OssUploadLogService extends IService<OssUploadLog> {
    /**
     * 记录文件上传日志信息的方法。
     *
     * @param filename 上传的文件名
     * @param size     文件的大小（以字节为单位）
     * @param filepath 文件的访问路径
     * @param ossPath  文件在 OSS 中存储的路径
     * @return 是否记录成功
     */
    boolean log(String filename, Long size, String filepath, String ossPath);

    /**
     * 获取指定用户未被删除的文件 Oss 路径列表。
     *
     * @param userId 用户的唯一标识
     * @return 未被删除的文件 Oss 路径列表
     */
    Set<String> getUndeletedFileOssPath(Long userId);
}