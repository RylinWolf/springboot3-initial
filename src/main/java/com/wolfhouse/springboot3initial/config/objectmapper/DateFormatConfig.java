package com.wolfhouse.springboot3initial.config.objectmapper;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Rylin Wolf
 */
@ConfigurationProperties(prefix = "custom.dateformat")
public record DateFormatConfig(String datetime, String date, String time) {
}
