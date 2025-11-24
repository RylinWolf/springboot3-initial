package com.wolfhouse.springboot3initial.task;

import com.wolfhouse.springboot3initial.config.objectmapper.JacksonObjectMapper;
import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.UserRedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.wolfhouse.springboot3initial.task.TaskConstant.SCHEDULE_COMPLETED;
import static com.wolfhouse.springboot3initial.task.TaskConstant.SCHEDULE_STARTED;

/**
 * 用户、管理员、验证业务 相关的定时任务
 *
 * @author Rylin Wolf
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdminAuthTask {

    private final ServiceRedisUtil redisUtil;
    private final UserAdminAuthMediator mediator;
    private JacksonObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(@Qualifier("redisObjectMapper") JacksonObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 定时任务方法：将 Redis 中记录的用户最后登录时间更新到数据库中。
     * 该方法通过扫描 Redis 中的键值对，获取用户的最后登录信息，并将其移至数据库中，确保数据持久化。
     * 方法每两天凌晨 3:30 执行一次（基于 @Scheduled 的 cron 表达式）。
     * <p>
     * 主要执行步骤：
     * 1. 扫描 Redis 中匹配特定格式的键，获取用户最后登录的相关数据。
     * 2. 从 Redis 中删除对应键值对，同时将数据转换为本地缓存中的 Map。
     * 3. 调用中介服务，将本地缓存中的用户最后登录时间更新到数据库中。
     * <p>
     * 异常处理：
     * - 如果 Redis 键值为空或格式错误，记录相关日志信息。
     * - 捕获并记录数据转换或其他操作中的任意异常，确保任务不中断。
     * <p>
     * 特别说明：
     * - Redis 键的格式基于通配符匹配，具体由 {@link UserRedisConstant#LAST_LOGIN_WITH_FORMAT} 定义。
     * - Redis 键中用户 ID 的解析依赖于分隔符，分隔符通过 {@code redisUtil.redisKeyUtil.getSeparator()} 获取。
     * - 本地缓存最终通过 {@link UserAdminAuthMediator#updateAccessedTime(Map)} 方法批量更新到数据库。
     */
    @Scheduled(cron = "0 30 3 */2 * *")
    public void lastLoginRedisToDb() {
        log.info(SCHEDULE_STARTED);
        // 初始化【最后登录用户】映射
        Map<Long, LocalDateTime> lastLogins = new HashMap<>(1000);
        // 扫描所有最后登录键
        try {
            Set<String> logins = redisUtil.keysMatch(UserRedisConstant.LAST_LOGIN_WITH_FORMAT, 1000);
            for (String login : logins) {
                // 键为空（意外）
                if (login == null || login.isBlank()) {
                    log.error("{}: {}", TaskConstant.FAILED, "login is null or blank");
                    continue;
                }
                try {
                    // 获取并删除最后登录记录，保存至本地缓存（由于键是 Redis 键，不由工具管理，所以这里必须用 redis 的方法）
                    Object o = redisUtil.opsForValue.getAndDelete(login);
                    LocalDateTime time = objectMapper.convertValue(o, LocalDateTime.class);
                    // 获取最后一位，即 用户 ID
                    String[] split = login.split(redisUtil.redisKeyUtil.getSeparator());
                    lastLogins.put(Long.parseLong(split[split.length - 1]), time);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            // 更新最后登录日期
            mediator.updateAccessedTime(lastLogins);
        } catch (Exception e) {
            log.error("{}: {}", TaskConstant.FAILED, e.getMessage(), e);
        }

        log.info("{}", SCHEDULE_COMPLETED);
    }
}
