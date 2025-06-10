package com.onework.boot.framework.tenant.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@ConditionalOnProperty(prefix = "onework.tenant", value = "enable", matchIfMissing = true) // 允许使用 onework.tenant.enable=false 禁用多租户
@EnableConfigurationProperties(TenantProperties.class)
public class OneworkTenantAutoConfiguration {
}
