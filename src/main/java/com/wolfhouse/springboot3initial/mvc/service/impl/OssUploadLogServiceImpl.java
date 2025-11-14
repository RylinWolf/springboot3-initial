package com.wolfhouse.springboot3initial.mvc.service.impl;


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
    public boolean log(String filename, Long size, String filepath) {
        UserLocalDto login = SecurityContextUtil.getLoginUser();
        if (login == null) {
            throw new ServiceException(HttpCode.UN_AUTHORIZED);
        }
        return save(OssUploadLog.builder()
                                .filename(filename)
                                .fileSize(size)
                                .filePath(filepath)
                                .uploadUser(login.getId())
                                .build());
    }
}