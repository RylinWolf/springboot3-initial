package com.wolfhouse.springboot3initial.task;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Rylin Wolf
 */
@Aspect
@Component
@Slf4j
public class ScheduledAspect {

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object aroundScheduledTask(ProceedingJoinPoint joinPoint) throws Throwable {
        String taskName = joinPoint.getSignature()
                                   .toShortString();
        log.info("{}: {}", TaskConstant.SCHEDULE_STARTED, taskName);

        try {
            Object result = joinPoint.proceed();
            log.info("{}: {}", TaskConstant.SCHEDULE_COMPLETED, taskName);
            return result;

        } catch (Exception e) {
            log.error("{}: {}, {}", TaskConstant.FAILED, taskName, e.getMessage(), e);
            // 可以选择是否重新抛出
            // throw e;
            return null;
        }
    }
}