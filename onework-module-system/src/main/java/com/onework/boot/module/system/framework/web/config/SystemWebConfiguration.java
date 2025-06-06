package com.onework.boot.module.system.framework.web.config;

import com.onework.boot.framework.swagger.config.OneworkSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的 web 组件的 Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return OneworkSwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
