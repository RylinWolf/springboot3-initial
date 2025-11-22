package com.wolfhouse.springboot3initial.mvc.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mvc.mapper.OssUploadLogMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.OssUploadLog;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.service.OssUploadLogService;
import com.wolfhouse.springboot3initial.security.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wolfhouse.springboot3initial.mvc.model.domain.table.OssUploadLogTableDef.OSS_UPLOAD_LOG;

/**
 * 服务层实现。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Slf4j
@Service
public class OssUploadLogServiceImpl extends ServiceImpl<OssUploadLogMapper, OssUploadLog> implements OssUploadLogService {
    @Override
    public boolean log(String filename, Long size, String filepath, String ossPath) {
        UserLocalDto login = SecurityContextUtil.getLoginUser();
        if (login == null) {
            throw new ServiceException(HttpCode.UN_AUTHORIZED);
        }
        return save(OssUploadLog.builder()
                                .filename(filename)
                                .fileSize(size)
                                .filePath(filepath)
                                .fileOssPath(ossPath)
                                .uploadUser(login.getId())
                                .build());
    }

    @Override
    public Set<String> getUndeletedFileOssPath(Long userId) {
        // 每次更新头像时都应调用，所以这里不用做分页
        List<OssUploadLog> ossUploadLogs = mapper.selectListByQuery(QueryWrapper.create()
                                                                                .from(OSS_UPLOAD_LOG)
                                                                                .select(OSS_UPLOAD_LOG.FILE_OSS_PATH)
                                                                                .where(OSS_UPLOAD_LOG.UPLOAD_USER.eq(
                                                                                    userId)));
        return ossUploadLogs.stream()
                            .map(OssUploadLog::getFileOssPath)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
    }
}