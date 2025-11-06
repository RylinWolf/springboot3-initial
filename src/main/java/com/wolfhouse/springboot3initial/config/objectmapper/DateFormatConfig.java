package com.wolfhouse.springboot3initial.config.objectmapper;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日期格式配置类。
 * <p>
 * 需启用 ConfigurationPropertiesScan
 *
 * @author Rylin Wolf
 */
@ConfigurationProperties(prefix = "custom.dateformat")
public record DateFormatConfig(String datetime, String date, String time) {
}
