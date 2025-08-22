package com.onework.boot.framework.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置属性
 */
@Data
@ConfigurationProperties(prefix = "onework.captcha")
public class CaptchaProperties {

    /**
     * 验证码类型
     */
    private CaptchaType type = CaptchaType.ARITHMETIC;

    /**
     * 验证码宽度
     */
    private Integer width = 130;

    /**
     * 验证码高度
     */
    private Integer height = 48;

    /**
     * 验证码长度
     */
    private Integer length = 4;

    /**
     * 验证码有效期（分钟）
     */
    private Integer expireMinutes = 5;

    /**
     * 验证码前缀
     */
    private String prefix = "captcha:";

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 验证码类型枚举
     */
    public enum CaptchaType {
        /**
         * 算术验证码
         */
        ARITHMETIC,
        /**
         * 字符验证码
         */
        CHARACTER,
        /**
         * GIF验证码
         */
        GIF
    }
} 