package com.onework.boot.framework.captcha.config;

import com.onework.boot.framework.captcha.core.service.CaptchaService;
import com.onework.boot.framework.captcha.core.service.impl.CaptchaServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 验证码自动配置类
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(CaptchaProperties captchaProperties, StringRedisTemplate redisTemplate) {
        return new CaptchaServiceImpl(captchaProperties, redisTemplate);
    }
} 