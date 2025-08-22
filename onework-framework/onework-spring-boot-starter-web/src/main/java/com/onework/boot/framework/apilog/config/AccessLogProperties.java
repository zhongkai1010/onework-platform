package com.onework.boot.framework.apilog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 访问日志配置属性
 */
@Data
@ConfigurationProperties(prefix = "onework.access-log")
public class AccessLogProperties {
    /**
     * 是否启用访问日志
     */
    private boolean enabled = true;
} 