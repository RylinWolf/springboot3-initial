package com.wolfhouse.springboot3initial.config.util;

import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rylin Wolf
 */
@Configuration
public class ThrowUtilConfig {
    @PostConstruct
    public void exceptionInit() {
        ThrowUtil.exceptionClazz = ServiceException.class;
    }
}
