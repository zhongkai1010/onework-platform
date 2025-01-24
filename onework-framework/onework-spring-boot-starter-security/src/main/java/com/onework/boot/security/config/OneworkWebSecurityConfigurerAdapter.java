package com.onework.boot.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 自定义的 Spring Security 配置适配器实现
 *
 */
@AutoConfiguration
@AutoConfigureOrder(-1) // 目的：先于 Spring Security 自动配置，避免一键改包后，org.* 基础包无法生效
@EnableMethodSecurity(securedEnabled = true)
public class OneworkWebSecurityConfigurerAdapter {
}
