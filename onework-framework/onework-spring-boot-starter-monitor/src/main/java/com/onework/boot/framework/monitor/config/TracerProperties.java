package com.onework.boot.framework.monitor.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 * 
 */
@ConfigurationProperties("onework.tracer")
@Data
public class TracerProperties {
}
