package com.wolfhouse.springboot3initial.util.redisutil.service;

import com.wolfhouse.springboot3initial.mvc.service.OssUploadLogService;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.OssRedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Rylin Wolf
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisOssService {
    private final ServiceRedisUtil redisUtil;
    private final OssUploadLogService ossLogService;

    public Boolean addDeletesFromOss(Long userId, Set<String> except) {
        Set<String> filePaths = ossLogService.getUndeletedFileOssPath(userId);
        filePaths.removeAll(except);
        return this.addDeletes(filePaths);
    }

    public Boolean addDeletes(Set<String> deletes) {
        log.debug("添加至 Redis Oss 删除列表");
        Long amount = redisUtil.addSetValue(OssRedisConstant.OSS_DELETE_SET, deletes.toArray());
        if (amount != deletes.size()) {
            log.warn("添加至 Redis Oss 删除列表时，有成员未成功添加! {}", deletes);
            return false;
        }
        return true;
    }

    public Set<String> getDeletes() {
        Set<Object> deletes = redisUtil.getSetMembers(OssRedisConstant.OSS_DELETE_SET);
        return deletes.stream()
                      .map(String::valueOf)
                      .collect(java.util.stream.Collectors.toSet());
    }
}
